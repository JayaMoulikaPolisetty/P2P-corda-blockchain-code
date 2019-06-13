package com.template.contracts;

import com.template.states.LenderBorrowerState;
import com.template.states.LendingProposalState;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.contracts.ContractState;
import net.corda.core.transactions.LedgerTransaction;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.List;

import static net.corda.core.contracts.ContractsDSL.requireThat;

public class LendingProposalContract implements Contract {

    public static final String LENDING_PROPOSAL_ID = "com.template.contracts.LendingProposalContract";

    @Override
    public void verify(@NotNull LedgerTransaction tx) throws IllegalArgumentException {

        if (tx != null && tx.getCommands().size() != 1) {
            throw new IllegalArgumentException("Transaction must have one command");
        }

        List<PublicKey> requiredSigners = tx.getCommand(0).getSigners();

        if (tx.getCommand(0).getValue() instanceof Commands.LendingProposal) {
            verifyLendingProposal(tx, requiredSigners);
        }else if(tx.getCommand(0).getValue() instanceof Commands.LendingBorrower){
            verifyLendingBorrower(tx,requiredSigners);
        }
        else {
            throw new IllegalArgumentException("Command is not valid");
        }
    }

    private void verifyLendingProposal(LedgerTransaction tx, List<PublicKey> requiredSigners) {
            requireThat(req -> {

                req.using("0 input should be consumed while proposal of lending", tx.getInputStates().isEmpty());
                req.using("Only one output should be created during the process of lending proposal", tx.getOutputStates().size() == 1);

                ContractState outputState = tx.getOutput(0);
                req.using(" Output must be a UserState", outputState instanceof LendingProposalState);
                LendingProposalState lendingProposalState = (LendingProposalState)outputState;

                PublicKey myKey = lendingProposalState.getMyParty().getOwningKey();
                PublicKey otherPartyKey = lendingProposalState.getOtherParty().getOwningKey();

                req.using("Company should sign the transaction", requiredSigners.contains(myKey));
                req.using("OtherPlatform should sign the transaction", requiredSigners.contains(otherPartyKey));
                return null;
            });

    }

    private void verifyLendingBorrower(LedgerTransaction tx, List<PublicKey> requiredSigners) {
        requireThat(req -> {

            req.using("One input should be consumed while proposal of lending", tx.getInputStates().size() == 1);
            req.using("One output should be created during the process of lending proposal", tx.getOutputStates().size() == 1);

            ContractState inputState = tx.getInput(0);
            req.using("input must be a LendingProposalState", inputState instanceof LendingProposalState);
            ContractState outputState = tx.getOutput(0);
            req.using(" Output must be a LenderBorrowerState", outputState instanceof LenderBorrowerState);
            LenderBorrowerState lenderBorrowerState = (LenderBorrowerState)outputState;

            PublicKey myKey = lenderBorrowerState.getMyParty().getOwningKey();
            PublicKey otherPartyKey = lenderBorrowerState.getOtherParty().getOwningKey();

            req.using("Company should sign the transaction", requiredSigners.contains(myKey));
            req.using("OtherPlatform should sign the transaction", requiredSigners.contains(otherPartyKey));
            return null;
        });
    }

    public interface Commands extends CommandData {
        class LendingProposal implements LendingProposalContract.Commands { }
        class LendingBorrower implements LendingProposalContract.Commands {}
    }
}
