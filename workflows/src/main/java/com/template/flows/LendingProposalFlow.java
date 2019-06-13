package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.template.contracts.LendingProposalContract;
import com.template.states.LendingProposalState;
import net.corda.core.contracts.Command;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;

@InitiatingFlow
@StartableByRPC
public class LendingProposalFlow extends FlowLogic<SignedTransaction> {

    private LendingProposalState lendingProposalState;

    public LendingProposalFlow(LendingProposalState lendingProposalState) {
        this.lendingProposalState = lendingProposalState;
    }

    private final ProgressTracker.Step LENDING_PROPOSAL_REQUEST = new ProgressTracker.Step("Lending proposal application for company");
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
            LENDING_PROPOSAL_REQUEST,
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

        getLogger().info("Entered into flow");
        final Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);
        progressTracker.setCurrentStep(LENDING_PROPOSAL_REQUEST);
        final Command<LendingProposalContract.Commands.LendingProposal> lendingProposalCommand = new Command<>(new LendingProposalContract.Commands.LendingProposal(), ImmutableList.of(lendingProposalState.getMyParty().getOwningKey(), lendingProposalState.getOtherParty().getOwningKey()));

        final TransactionBuilder lendingTransactionBuilder = new TransactionBuilder(notary)
                .addOutputState(lendingProposalState, LendingProposalContract.LENDING_PROPOSAL_ID)
                .addCommand(lendingProposalCommand);

        progressTracker.setCurrentStep(VERIFYING_TRANSACTION);
        lendingTransactionBuilder.verify(getServiceHub());

        progressTracker.setCurrentStep(SIGNING_TRANSACTION);
        final SignedTransaction partlySignedTx = getServiceHub().signInitialTransaction(lendingTransactionBuilder);
        System.out.println("Partly SignedTransaction is  "+partlySignedTx);
        progressTracker.setCurrentStep(GATHERING_SIGNS);
        FlowSession otherPartySession = initiateFlow(lendingProposalState.getOtherParty());
        final SignedTransaction fullySignedTx = subFlow(new CollectSignaturesFlow(partlySignedTx, ImmutableSet.of(otherPartySession), CollectSignaturesFlow.Companion.tracker()));
        progressTracker.setCurrentStep(FINALISING_TRANSACTION);
        System.out.println("Fully SignedTransaction is  "+fullySignedTx);
        return subFlow(new FinalityFlow(fullySignedTx,ImmutableSet.of(otherPartySession)));
    }
}
