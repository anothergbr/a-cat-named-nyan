package com.gbr.nyan.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class User extends UserBasicHardcodedSpringSecurityStuff {
    private final static long serialVersionUID = -1;

    @Id
    private String email;
    @Column(unique = true)
    private String openIdUrl;
    @Column(unique = true)
    private String uuid;
    @Column
    private String fullName;

    @ManyToOne
    private Account account;

    public User() {
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOpenIdUrl() {
        return openIdUrl;
    }

    public void setOpenIdUrl(String openIdUrl) {
        this.openIdUrl = openIdUrl;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
