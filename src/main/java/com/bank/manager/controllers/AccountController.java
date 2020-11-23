package com.bank.manager.controllers;

import com.bank.manager.models.Account;
import com.bank.manager.repositories.AccountRepository;
import com.bank.manager.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankManger/account")
public class AccountController {
    @Autowired
    AccountRepository repository;
    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/create")
    public String create(@RequestBody Account account) {
        if (customerRepository.existsById(account.getCustomerId())) {
            repository.save(new Account(account.getAccountType(), account.getCustomerId(), account.getAccountBalance()));
            //if account of same type exists already for the same customer, don't create
            return "account is created";
        } else
            return "customer doesn't exist";
    }

    @RequestMapping("/search/{id}")
    public String search(@PathVariable long id) {
        return repository.findById(id).toString();
    }

    @RequestMapping("/searchByCustId/{id}")
    public List<Account> fetchDataByEmployeeName(@PathVariable long id) {
        return repository.findByCustomerId(id);
    }
}
