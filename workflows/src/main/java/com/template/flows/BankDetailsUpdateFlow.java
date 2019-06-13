package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.template.contracts.UserContract;
import com.template.states.UserBankDetailsState;
import net.corda.core.contracts.Command;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;
import org.slf4j.LoggerFactory;

@InitiatingFlow
@StartableByRPC
public class BankDetailsUpdateFlow extends FlowLogic<SignedTransaction> {

    private UserBankDetailsState userState;
    private StateAndRef inputStateAndRef;

    public BankDetailsUpdateFlow(UserBankDetailsState userState, StateAndRef inputStateAndRef) {
        this.userState = userState;
        this.inputStateAndRef = inputStateAndRef;
      }


    private final ProgressTracker.Step USER_REG_BANK_REQUEST = new ProgressTracker.Step("User sending registration application for company");
    private final ProgressTracker.Step VERIFYING_TRANSACTION = new ProgressTracker.Step("Verifying contract constraints.");
    private final ProgressTracker.Step PLATFORM_RESPONSE = new ProgressTracker.Step("Sending response from Bank");
    private final ProgressTracker.Step SIGNING_TRANSACTION = new ProgressTracker.Step("Signing transaction with our private key.");
    private final ProgressTracker.Step GATHERING_SIGNS = new ProgressTracker.Step("Gathering the counterparty's signature.") {
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
            USER_REG_BANK_REQUEST,
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

        final Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);
        progressTracker.setCurrentStep(USER_REG_BANK_REQUEST);
        final Command<UserContract.Commands.UserBankDetailsUpdate> userBankDetailsUpdateCommand = new Command<>(new UserContract.Commands.UserBankDetailsUpdate(), ImmutableList.of(userState.getMyParty().getOwningKey(), userState.getOtherParty().getOwningKey()));

        final TransactionBuilder txBuilder = new TransactionBuilder(notary)
                .addInputState(inputStateAndRef)
                .addOutputState(userState)
                .addCommand(userBankDetailsUpdateCommand);

        progressTracker.setCurrentStep(VERIFYING_TRANSACTION);
        txBuilder.verify(getServiceHub());

        progressTracker.setCurrentStep(SIGNING_TRANSACTION);
        final SignedTransaction partSignedTx = getServiceHub().signInitialTransaction(txBuilder);
        LoggerFactory.getLogger(BankDetailsUpdateFlow.class).info("Bank details partSignedTx: "+partSignedTx);
        progressTracker.setCurrentStep(GATHERING_SIGNS);
        FlowSession otherPartySession = initiateFlow(userState.getOtherParty());
        final SignedTransaction fullySignedTx = subFlow(new CollectSignaturesFlow(partSignedTx, ImmutableSet.of(otherPartySession), CollectSignaturesFlow.Companion.tracker()));
        progressTracker.setCurrentStep(FINALISING_TRANSACTION);

        return subFlow(new FinalityFlow(fullySignedTx,ImmutableList.of(otherPartySession)));

    }

}
