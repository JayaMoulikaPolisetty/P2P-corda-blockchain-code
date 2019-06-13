package com.template.beans;

import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.serialization.CordaSerializable;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlRootElement
@CordaSerializable
public class LenderBorrowerBean {

    private UniqueIdentifier lender_id;
    private UniqueIdentifier borrower_id;
    private UniqueIdentifier loanNumber;
    private LocalDateTime contract_enforcement_time;


    public LenderBorrowerBean(UniqueIdentifier lender_id, UniqueIdentifier borrower_id, UniqueIdentifier loanNumber, LocalDateTime contract_enforcement_time) {
        this.lender_id = lender_id;
        this.borrower_id = borrower_id;
        this.loanNumber = loanNumber;
        this.contract_enforcement_time = contract_enforcement_time;
    }

    public UniqueIdentifier getLender_id() {
        return lender_id;
    }

    public void setLender_id(UniqueIdentifier lender_id) {
        this.lender_id = lender_id;
    }

    public UniqueIdentifier getBorrower_id() {
        return borrower_id;
    }

    public void setBorrower_id(UniqueIdentifier borrower_id) {
        this.borrower_id = borrower_id;
    }

    public UniqueIdentifier getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(UniqueIdentifier loanNumber) {
        this.loanNumber = loanNumber;
    }

    public LocalDateTime getContract_enforcement_time() {
        return contract_enforcement_time;
    }

    public void setContract_enforcement_time(LocalDateTime contract_enforcement_time) {
        this.contract_enforcement_time = contract_enforcement_time;
    }

    @Override
    public String toString() {
        return "LenderBorrowerBean{" +
                "lender_id=" + lender_id +
                ", borrower_id=" + borrower_id +
                ", loanNumber=" + loanNumber +
                ", contract_enforcement_time=" + contract_enforcement_time +
                '}';
    }
}
