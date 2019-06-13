package com.template.contracts;

import com.template.states.UserBankDetailsState;
import com.template.states.UserState;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.contracts.ContractState;
import net.corda.core.transactions.LedgerTransaction;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.List;

import static net.corda.core.contracts.ContractsDSL.requireThat;

public class UserContract implements Contract {

    public static final String USER_REGISTRATION_CONTRACT_ID = "com.template.contracts.UserContract";

    @Override
    public void verify(@NotNull LedgerTransaction tx) throws IllegalArgumentException {

        if(tx!= null && tx.getCommands().size()!= 1){
            throw new IllegalArgumentException("Transaction must have one command");
        }

        List<PublicKey> requiredSigners = tx.getCommand(0).getSigners();

        if (tx.getCommand(0).getValue() instanceof Commands.UserRegistration){
            verifyUserReg(tx,requiredSigners);
        }
        else if(tx.getCommand(0).getValue() instanceof Commands.UserBankDetailsUpdate){
            verifyUserBankDetails(tx,requiredSigners);
        }
        else {
            throw new IllegalArgumentException("Command is not valid");
        }


    }

    private void verifyUserBankDetails(LedgerTransaction tx, List<PublicKey> requiredSigners) {

        requireThat(req -> {

        //    req.using("One input should be consumed while updating user", tx.getInputStates().size() == 1);
            req.using("one input should be consumed while adding bank details user", tx.getInputStates().size() == 1);

            req.using("Only one output should be created during the process of registering user", tx.getOutputStates().size() == 1);

            ContractState inputState = tx.getInput(0);
            req.using(" input must be a UserState", inputState instanceof UserState);

            ContractState outputState = tx.getOutput(0);
            req.using(" Output must be a UserBankDetailsState", outputState instanceof UserBankDetailsState);
            UserBankDetailsState userBankDetailsState = (UserBankDetailsState) outputState;


            PublicKey myKey = userBankDetailsState.getMyParty().getOwningKey();
            PublicKey otherPartyKey = userBankDetailsState.getOtherParty().getOwningKey();

            req.using("Company should sign the transaction", requiredSigners.contains(myKey));
            req.using("OtherPlatform should sign the transaction", requiredSigners.contains(otherPartyKey));
            return null;
        });
    }

    private void verifyUserReg(LedgerTransaction tx, List<PublicKey> requiredSigners) {
        requireThat(req -> {

            req.using(" input should be consumed while registering user", tx.getInputStates().isEmpty());
            req.using("Only one output should be created during the process of registering user", tx.getOutputStates().size() == 1);

            ContractState outputState = tx.getOutput(0);
            req.using(" Output must be a UserState", outputState instanceof UserState);
            UserState userState = (UserState)outputState;

            PublicKey myKey = userState.getMyParty().getOwningKey();
            PublicKey otherPartyKey = userState.getOtherParty().getOwningKey();

            req.using("Company should sign the transaction", requiredSigners.contains(myKey));
            req.using("OtherPlatform should sign the transaction", requiredSigners.contains(otherPartyKey));
            return null;
        });
    }

    public interface Commands extends CommandData {
        class  UserRegistration implements UserContract.Commands { }
        class UserBankDetailsUpdate implements UserContract.Commands { }

    }

}
