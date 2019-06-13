package com.template.beans;

import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.serialization.CordaSerializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@CordaSerializable
public class UserBankDetails {


    private String panNumber;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    private String branchName;
    private UniqueIdentifier userId;
    private String aadharCard;
    private AddressBean address;

    public UserBankDetails(String panNumber, String accountNumber, String ifscCode, String bankName, String branchName, UniqueIdentifier userId,String aadharCard, AddressBean address) {
        this.panNumber = panNumber;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.bankName = bankName;
        this.branchName = branchName;
        this.userId = userId;
        this.aadharCard = aadharCard;
        this.address = address;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public UniqueIdentifier getUserId() {
        return userId;
    }

    public void setUserId(UniqueIdentifier userId) {
        this.userId = userId;
    }


    public String getAadharCard() {
        return aadharCard;
    }

    public void setAadharCard(String aadharCard) {
        this.aadharCard = aadharCard;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserBankDetails{" +
                "panNumber='" + panNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", userId=" + userId +
                ", aadharCard='" + aadharCard + '\'' +
                ", address=" + address +
                '}';
    }
}
