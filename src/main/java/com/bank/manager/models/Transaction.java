package com.bank.manager.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;

    @Column(name = "value")
    private float value;

    @Column(name = "customerId")
    private long customerId;

    @Column(name = "date")
    private Date date;

    @Column(name = "accountId")

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    private long accountId;

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    protected Transaction() {
    }

    public Transaction(float value, long customerId, long accountId) {
        this.value = value;
        this.customerId = customerId;
        this.accountId = accountId;
        this.date = new Date(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return String.format("Transaction [id=%d, TransactionValue='%f', customerId='%d', date='%s']", transactionId, value, customerId, date);
    }
}
