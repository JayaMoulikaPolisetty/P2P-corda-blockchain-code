package com.template.beans;

import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.serialization.CordaSerializable;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlRootElement
@CordaSerializable
public class PaymentDetails {

    private UniqueIdentifier loanNumber;
    private LocalDateTime payment_done_on;
    private double amount_paid;
    private double total_amount_to_be_paid;
    //private double next_payment;
    private double default_fine = 0;
    private LocalDateTime next_payment_date;
    private double next_payment_amount;
    private LocalDateTime last_payment_date;
    private LocalDateTime contract_enforcement_time;

    public PaymentDetails(UniqueIdentifier loanNumber, LocalDateTime payment_done_on, double amount_paid, double total_amount_to_be_paid, double default_fine, LocalDateTime next_payment_date, double next_payment_amount, LocalDateTime last_payment_date, LocalDateTime contract_enforcement_time) {
        this.loanNumber = loanNumber;
        this.payment_done_on = payment_done_on;
        this.amount_paid = amount_paid;
        this.total_amount_to_be_paid = total_amount_to_be_paid;
        //this.next_payment = next_payment;
        this.default_fine = default_fine;
        this.next_payment_date = next_payment_date;
        this.next_payment_amount = next_payment_amount;
        this.last_payment_date = last_payment_date;
        this.contract_enforcement_time = contract_enforcement_time;
    }

    public UniqueIdentifier getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(UniqueIdentifier loanNumber) {
        this.loanNumber = loanNumber;
    }

    public LocalDateTime getPayment_done_on() {
        return payment_done_on;
    }

    public void setPayment_done_on(LocalDateTime payment_done_on) {
        this.payment_done_on = payment_done_on;
    }

    public double getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(double amount_paid) {
        this.amount_paid = amount_paid;
    }

    public double getTotal_amount_to_be_paid() {
        return total_amount_to_be_paid;
    }

    public void setTotal_amount_to_be_paid(double total_amount_to_be_paid) {
        this.total_amount_to_be_paid = total_amount_to_be_paid;
    }

//    public double getNext_payment() {
//        return next_payment;
//    }
//
//    public void setNext_payment(double next_payment) {
//        this.next_payment = next_payment;
//    }

    public double getDefault_fine() {
        return default_fine;
    }

    public void setDefault_fine(double default_fine) {
        this.default_fine = default_fine;
    }

    public LocalDateTime getNext_payment_date() {
        return next_payment_date;
    }

    public void setNext_payment_date(LocalDateTime next_payment_date) {
        this.next_payment_date = next_payment_date;
    }

    public double getNext_payment_amount() {
        return next_payment_amount;
    }

    public void setNext_payment_amount(double next_payment_amount) {
        this.next_payment_amount = next_payment_amount;
    }

    public LocalDateTime getLast_payment_date() {
        return last_payment_date;
    }

    public void setLast_payment_date(LocalDateTime last_payment_date) {
        this.last_payment_date = last_payment_date;
    }

    public LocalDateTime getContract_enforcement_time() {
        return contract_enforcement_time;
    }

    public void setContract_enforcement_time(LocalDateTime contract_enforcement_time) {
        this.contract_enforcement_time = contract_enforcement_time;
    }

    @Override
    public String toString() {
        return "PaymentDetails{" +
                "loanNumber=" + loanNumber +
                ", payment_done_on=" + payment_done_on +
                ", amount_paid=" + amount_paid +
                ", total_amount_to_be_paid=" + total_amount_to_be_paid +
            //    ", next_payment=" + next_payment +
                ", default_fine=" + default_fine +
                ", next_payment_date=" + next_payment_date +
                ", next_payment_amount=" + next_payment_amount +
                ", last_payment_date=" + last_payment_date +
                ", contract_enforcement_time=" + contract_enforcement_time +
                '}';
    }
}
