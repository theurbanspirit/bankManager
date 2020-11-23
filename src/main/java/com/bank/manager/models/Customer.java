package com.bank.manager.models;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long customerId;

    @Column(name = "customerName")
    private String customerName;

    @Column(name = "customerAddress")
    private String customerAddress;

    @Column(name = "customerEmail")
    private String customerEmail;

    @Column(name = "contactEmployeeId")
    private long contactEmployeeId;


    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public long getContactEmployeeId() {
        return contactEmployeeId;
    }

    public void setContactEmployeeId(long contactEmployeeId) {
        this.contactEmployeeId = contactEmployeeId;
    }

    protected Customer() {
    }

    public Customer(String customerName, String customerAddress, String customerEmail, long contactEmployeeId) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerEmail = customerEmail;
        this.contactEmployeeId = contactEmployeeId;
    }

    @Override
    public String toString() {
        return String.format("Customer [id=%d, Name='%s', Type='%s']", customerId, customerName, contactEmployeeId);
    }
}
