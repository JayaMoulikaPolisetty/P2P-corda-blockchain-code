package com.template.beans;

import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.serialization.CordaSerializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@CordaSerializable
public class UserDetails {

    private String email;
    private UniqueIdentifier userId;


    public UserDetails(String email, UniqueIdentifier userId) {
        this.email = email;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UniqueIdentifier getUserId() {
        return userId;
    }

    public void setUserId(UniqueIdentifier userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "email='" + email + '\'' +
                ", userId=" + userId +
                '}';
    }
}
