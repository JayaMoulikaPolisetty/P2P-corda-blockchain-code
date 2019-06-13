package com.template.states;

import com.google.common.collect.ImmutableList;
import com.template.contracts.UserContract;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.serialization.ConstructorForDeserialization;
import net.corda.core.serialization.CordaSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@BelongsToContract(UserContract.class)
public class UserState implements LinearState, ContractState {

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private long mobile;
    private UniqueIdentifier userId;
    private Party myParty;
    private Party otherParty;


    public UserState(String firstName, String middleName, String lastName, String email, String password, long mobile, UniqueIdentifier userId, Party myParty, Party otherParty) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.userId = userId;
        this.myParty = myParty;
        this.otherParty = otherParty;
    }

    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return getUserId();
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(myParty,otherParty);
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
        return "UserState{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mobile=" + mobile +
                ", userId=" + userId +
                ", myParty=" + myParty +
                ", otherParty=" + otherParty +
                '}';
    }

}
