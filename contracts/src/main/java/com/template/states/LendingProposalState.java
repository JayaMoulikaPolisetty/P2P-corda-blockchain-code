package com.template.states;
import com.google.common.collect.ImmutableList;
import com.template.contracts.LendingProposalContract;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@BelongsToContract(LendingProposalContract.class)
public class LendingProposalState implements LinearState, ContractState {

    private String user_credit_type;
    private double interest_rate;
    private double amount;
    private int durationInMonths;
    private String loan_category;
    private String writeup;
    private UniqueIdentifier lender_id;
    private UniqueIdentifier loanNumber;
    private LocalDateTime contract_initiation_time;
    private double interest;
    private double platform_fee;
    private Party myParty;
    private Party otherParty;

    public LendingProposalState(String user_credit_type, double interest_rate, double amount, int durationInMonths, String loan_category, String writeup, UniqueIdentifier lender_id, UniqueIdentifier loanNumber, LocalDateTime contract_initiation_time, double interest, double platform_fee, Party myParty, Party otherParty) {
        this.user_credit_type = user_credit_type;
        this.interest_rate = interest_rate;
        this.amount = amount;
        this.durationInMonths = durationInMonths;
        this.loan_category = loan_category;
        this.writeup = writeup;
        this.lender_id = lender_id;
        this.loanNumber = loanNumber;
        this.contract_initiation_time = contract_initiation_time;
        this.interest = interest;
        this.platform_fee = platform_fee;
        this.myParty = myParty;
        this.otherParty = otherParty;
    }

    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return loanNumber;
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(myParty,otherParty);
    }

    public String getUser_credit_type() {
        return user_credit_type;
    }

    public void setUser_credit_type(String user_credit_type) {
        this.user_credit_type = user_credit_type;
    }

    public double getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(double interest_rate) {
        this.interest_rate = interest_rate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }

    public void setDurationInMonths(int durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

    public String getLoan_category() {
        return loan_category;
    }

    public void setLoan_category(String loan_category) {
        this.loan_category = loan_category;
    }

    public String getWriteup() {
        return writeup;
    }

    public void setWriteup(String writeup) {
        this.writeup = writeup;
    }

    public UniqueIdentifier getLender_id() {
        return lender_id;
    }

    public void setLender_id(UniqueIdentifier lender_id) {
        this.lender_id = lender_id;
    }

    public UniqueIdentifier getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(UniqueIdentifier loanNumber) {
        this.loanNumber = loanNumber;
    }

    public LocalDateTime getContract_initiation_time() {
        return contract_initiation_time;
    }

    public void setContract_initiation_time(LocalDateTime contract_initiation_time) {
        this.contract_initiation_time = contract_initiation_time;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getPlatform_fee() {
        return platform_fee;
    }

    public void setPlatform_fee(double platform_fee) {
        this.platform_fee = platform_fee;
    }

    public Party getMyParty() {
        return myParty;
    }

    public void setMyParty(Party myParty) {
        this.myParty = myParty;
    }

    public Party getOtherParty() {
        return otherParty;
    }

    public void setOtherParty(Party otherParty) {
        this.otherParty = otherParty;
    }

    @Override
    public String toString() {
        return "LendingProposalState{" +
                "user_credit_type='" + user_credit_type + '\'' +
                ", interest_rate=" + interest_rate +
                ", amount=" + amount +
                ", durationInMonths=" + durationInMonths +
                ", loan_category='" + loan_category + '\'' +
                ", writeup='" + writeup + '\'' +
                ", lender_id=" + lender_id +
                ", loanNumber=" + loanNumber +
                ", contract_initiation_time=" + contract_initiation_time +
                ", interest=" + interest +
                ", platform_fee=" + platform_fee +
                ", myParty=" + myParty +
                ", otherParty=" + otherParty +
                '}';
    }
}
