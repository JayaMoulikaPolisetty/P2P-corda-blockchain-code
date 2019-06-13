package com.template.beans;

import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.serialization.CordaSerializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@CordaSerializable
public class LendingProposalBean {

    private String user_credit_type;
    private double interest_rate;
    private double amount;
    private int durationInMonths;
    private String loan_category;
    private String writeup;
    private UniqueIdentifier lender_id;


    public LendingProposalBean(String user_credit_type, double interest_rate, double amount, int durationInMonths, String loan_category, String writeup, UniqueIdentifier lender_id) {
        this.user_credit_type = user_credit_type;
        this.interest_rate = interest_rate;
        this.amount = amount;
        this.durationInMonths = durationInMonths;
        this.loan_category = loan_category;
        this.writeup = writeup;
        this.lender_id = lender_id;
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

    @Override
    public String toString() {
        return "LendingProposalBean{" +
                "user_credit_type='" + user_credit_type + '\'' +
                ", interest_rate=" + interest_rate +
                ", amount=" + amount +
                ", durationInMonths=" + durationInMonths +
                ", loan_category='" + loan_category + '\'' +
                ", writeup='" + writeup + '\'' +
                ", lender_id=" + lender_id +
                '}';
    }
}
