package com.template.beans;

import com.template.states.LenderBorrowerState;
import com.template.states.LendingProposalState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.Party;
import net.corda.core.serialization.CordaSerializable;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@CordaSerializable
@XmlRootElement
public class LendingProposalDetails {

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
    private UniqueIdentifier borrower_id;
    private LocalDateTime contract_enforcement_time;

    public LendingProposalDetails(LendingProposalState state){
        this.user_credit_type = state.getUser_credit_type();
        this.interest_rate = state.getInterest_rate();
        this.amount = state.getAmount();
        this.durationInMonths = state.getDurationInMonths();
        this.loan_category = state.getLoan_category();
        this.writeup = state.getWriteup();
        this.lender_id = state.getLender_id();
        this.loanNumber = state.getLoanNumber();
        this.contract_initiation_time = state.getContract_initiation_time();
        this.interest = state.getInterest();
        this.platform_fee = state.getPlatform_fee();

    }

    public LendingProposalDetails(LenderBorrowerState state){
        this.user_credit_type = state.getUser_credit_type();
        this.interest_rate = state.getInterest_rate();
        this.amount = state.getAmount();
        this.durationInMonths = state.getDurationInMonths();
        this.loan_category = state.getLoan_category();
        this.writeup = state.getWriteup();
        this.lender_id = state.getLender_id();
        this.loanNumber = state.getLoanNumber();
        this.contract_initiation_time = state.getContract_initiation_time();
        this.interest = state.getInterest();
        this.platform_fee = state.getPlatform_fee();
        this.borrower_id = state.getBorrower_id();
        this.contract_enforcement_time = state.getContract_enforcement_time();
    }

    @Override
    public String toString() {
        return "LendingProposalDetails{" +
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
                ", borrower_id=" + borrower_id +
                ", contract_enforcement_time=" + contract_enforcement_time +
                '}';
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

    public UniqueIdentifier getBorrower_id() {
        return borrower_id;
    }

    public void setBorrower_id(UniqueIdentifier borrower_id) {
        this.borrower_id = borrower_id;
    }

    public LocalDateTime getContract_enforcement_time() {
        return contract_enforcement_time;
    }

    public void setContract_enforcement_time(LocalDateTime contract_enforcement_time) {
        this.contract_enforcement_time = contract_enforcement_time;
    }
}
