package com.bank.manager.models;

import com.bank.manager.enums.Role;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long employeeId;

    @Column(name = "employeeName")
    private String employeeName;

    @Column(name = "employeeAddress")
    private String employeeAddress;

    @Column(name = "employeeEmail")
    private String employeeEmail;

    @Column(name = "employeeType")
    @Enumerated(EnumType.STRING)
    private Role employeeType;

    protected Employee() {
    }

    public Employee(String employeeName, String employeeAddress, String employeeEmail, Role employeeType) {
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
        this.employeeEmail = employeeEmail;
        this.employeeType = employeeType;
    }

    @Override
    public String toString() {
        return String.format("Employee [id=%d, Name='%s', Type='%s']", employeeId, employeeName, employeeType);
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public Role getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(Role employeeType) {
        this.employeeType = employeeType;
    }

}
