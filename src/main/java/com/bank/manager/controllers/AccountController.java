package com.bank.manager.controllers;

import com.bank.manager.enums.AccountType;
import com.bank.manager.requestBodyTypes.AccountStatementQuery;
import com.bank.manager.models.Account;
import com.bank.manager.models.Transaction;
import com.bank.manager.repositories.AccountRepository;
import com.bank.manager.repositories.CustomerRepository;
import com.bank.manager.repositories.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bankManager/account")
public class AccountController {
    @Autowired
    AccountRepository repository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    @PostMapping("/create")
    public String create(@RequestBody Account account) {
        if (customerRepository.existsById(account.getCustomerId())) {
            if (repository.existsCustomerIdByAccountType(account.getCustomerId(), account.getAccountType().toString())) {
                return "account already exists for this customer";
            }
            repository.save(new Account(account.getAccountType(), account.getCustomerId(), account.getAccountBalance()));
            return "account is created";
        } else
            return "customer doesn't exist";
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    @RequestMapping("/searchByAcctId/{id}")
    public String search(@PathVariable long id) {
        return repository.findById(id).toString();
    }

    @PreAuthorize("permitAll()")
    @RequestMapping("/searchByCustName/{name}")
    public List<Account> fetchDataByEmployeeName(@PathVariable String name) {
        return repository.findByCustomerId(customerRepository.findByCustomerName(name).getCustomerId());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/getMyAccounts")
    public List<Account> getMyAccounts(Authentication authentication) {
        String customerName = authentication.getName();
        return repository.findByCustomerId(customerRepository.findByCustomerName(customerName).getCustomerId());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/getAcctStmntddMMYYYY")
    public List<Transaction> getAccountStatement(@RequestBody AccountStatementQuery accountStatementQuery, Authentication authentication) throws Exception {
        if (accountStatementQuery.getFromDate().compareTo(accountStatementQuery.getToDate()) > 0)
            throw new Exception("toDate less than from fromDate");
        String customerName = authentication.getName();
        return transactionRepository.getAccountStatement(parseStringToDate(accountStatementQuery.getFromDate()), parseStringToDate(accountStatementQuery.getToDate()), repository.findCustomerAccountByAccountType(customerRepository.findByCustomerName(customerName).getCustomerId(), AccountType.savings.toString()).getAccountId());
    }

    public Date parseStringToDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }

    //add 3.5% function
}
