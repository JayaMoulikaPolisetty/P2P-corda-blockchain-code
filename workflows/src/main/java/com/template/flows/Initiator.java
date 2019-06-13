package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.template.contracts.UserContract;
import com.template.states.UserState;
import net.corda.core.contracts.Command;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;

// ******************
// * Initiator flow *
// ******************
@InitiatingFlow
@StartableByRPC
public class Initiator extends FlowLogic<SignedTransaction> {

    private UserState userState;

    public Initiator(UserState userState) {
        this.userState = userState;
    }

    private final ProgressTracker.Step USER_REG_REQUEST = new ProgressTracker.Step("User sending registration application for company");
    private final ProgressTracker.Step VERIFYING_TRANSACTION = new ProgressTracker.Step("Verifying contract constraints.");
    private final ProgressTracker.Step PLATFORM_RESPONSE = new ProgressTracker.Step("Sending response from Bank");
    private final ProgressTracker.Step SIGNING_TRANSACTION = new ProgressTracker.Step("Signing transaction with our private key.");
    private final ProgressTracker.Step GATHERING_SIGNS = new ProgressTracker.Step("Gathering the counterParty's signature.") {
        public ProgressTracker childProgressTracker() {
            return CollectSignaturesFlow.Companion.tracker();
        }
    };

    private final ProgressTracker.Step FINALISING_TRANSACTION = new ProgressTracker.Step("Obtaining notary signature and recording transaction.") {
        public ProgressTracker childProgressTracker() {
            return FinalityFlow.Companion.tracker();
        }
    };

    private final ProgressTracker progressTracker = new ProgressTracker(
            USER_REG_REQUEST,
            VERIFYING_TRANSACTION,
            SIGNING_TRANSACTION,
            PLATFORM_RESPONSE,
            GATHERING_SIGNS,
            FINALISING_TRANSACTION
    );

    @Override
    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }

    @Suspendable
    @Override
    public SignedTransaction call() throws FlowException {
        // Initiator flow logic goes here.
        getLogger().info("Entered into flow");
        final Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);
        progressTracker.setCurrentStep(USER_REG_REQUEST);
        final Command<UserContract.Commands.UserRegistration> userRegistrationCommand = new Command<>(new UserContract.Commands.UserRegistration(), ImmutableList.of(userState.getMyParty().getOwningKey(), userState.getOtherParty().getOwningKey()));

        final TransactionBuilder txBuilder = new TransactionBuilder(notary)
                .addOutputState(userState, UserContract.USER_REGISTRATION_CONTRACT_ID)
                .addCommand(userRegistrationCommand);

        progressTracker.setCurrentStep(VERIFYING_TRANSACTION);
        txBuilder.verify(getServiceHub());

        progressTracker.setCurrentStep(SIGNING_TRANSACTION);
        final SignedTransaction partSignedTx = getServiceHub().signInitialTransaction(txBuilder);
        System.out.println(partSignedTx);
        progressTracker.setCurrentStep(GATHERING_SIGNS);
        FlowSession otherPartySession = initiateFlow(userState.getOtherParty());
        final SignedTransaction fullySignedTx = subFlow(new CollectSignaturesFlow(partSignedTx, ImmutableSet.of(otherPartySession), CollectSignaturesFlow.Companion.tracker()));
        progressTracker.setCurrentStep(FINALISING_TRANSACTION);
        return subFlow(new FinalityFlow(fullySignedTx,ImmutableSet.of(otherPartySession)));
    }


}
