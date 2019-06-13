package com.template.states;

import com.google.common.collect.ImmutableList;
import com.template.contracts.UserContract;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@BelongsToContract(UserContract.class)
public class UserBankDetailsState implements ContractState, LinearState {

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private long mobile;
    private UniqueIdentifier userId;
    private String panNumber;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    private String branchName;
    private String aadharCard;
    private String door_number;
    private String street_name;
    private String area;
    private String city;
    private String state;
    private long pin;
    private String landmark;
    private Party myParty;
    private Party otherParty;

    public UserBankDetailsState(String firstName, String middleName, String lastName, String email, String password, long mobile, UniqueIdentifier userId, String panNumber, String accountNumber, String ifscCode, String bankName, String branchName, String aadharCard, String door_number, String street_name, String area, String city, String state, long pin, String landmark, Party myParty, Party otherParty) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.userId = userId;
        this.panNumber = panNumber;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.bankName = bankName;
        this.branchName = branchName;
        this.aadharCard = aadharCard;
        this.door_number = door_number;
        this.street_name = street_name;
        this.area = area;
        this.city = city;
        this.state = state;
        this.pin = pin;
        this.landmark = landmark;
        this.myParty = myParty;
        this.otherParty = otherParty;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public UniqueIdentifier getUserId() {
        return userId;
    }

    public void setUserId(UniqueIdentifier userId) {
        this.userId = userId;
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

    public String getAadharCard() {
        return aadharCard;
    }

    public void setAadharCard(String aadharCard) {
        this.aadharCard = aadharCard;
    }

    public String getDoor_number() {
        return door_number;
    }

    public void setDoor_number(String door_number) {
        this.door_number = door_number;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getPin() {
        return pin;
    }

    public void setPin(long pin) {
        this.pin = pin;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
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
        return "UserBankDetailsState{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mobile=" + mobile +
                ", userId=" + userId +
                ", panNumber='" + panNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", aadharCard='" + aadharCard + '\'' +
                ", door_number='" + door_number + '\'' +
                ", street_name='" + street_name + '\'' +
                ", area='" + area + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pin=" + pin +
                ", landmark='" + landmark + '\'' +
                ", myParty=" + myParty +
                ", otherParty=" + otherParty +
                '}';
    }

    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return userId;
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(myParty,otherParty);
    }


}
