package com.bank.manager.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "account")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;

    @Column(name = "accountType")
    private String accountType;

    @Column(name = "customerId")
    private long customerId;

    @Column(name = "accountBalance")
    private float accountBalance;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public float getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    protected Account() {
    }

    public Account(String accountType, long customerId, float accountBalance) {
        this.accountType = accountType;
        this.customerId = customerId;
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return String.format("Account [id=%d, Type='%s', customerId='%d', accountBalance='%f']", accountId, accountType, customerId, accountBalance);
    }
}
