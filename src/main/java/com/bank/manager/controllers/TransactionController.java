package com.bank.manager.controllers;

import com.bank.manager.models.Account;
import com.bank.manager.models.Transaction;
import com.bank.manager.repositories.AccountRepository;
import com.bank.manager.repositories.CustomerRepository;
import com.bank.manager.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankManager/transaction")
public class TransactionController {
    @Autowired
    TransactionRepository repository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/create")
    public String create(@RequestBody Transaction transaction) {
        if (customerRepository.existsById(transaction.getCustomerId()) &&
                accountRepository.existsById(transaction.getAccountId())) {
            repository.save(new Transaction(transaction.getValue(), transaction.getCustomerId(), transaction.getAccountId()));
            Account currentAccount = accountRepository.findByAccountId(transaction.getAccountId());
            currentAccount.setAccountBalance(currentAccount.getAccountBalance() + transaction.getValue());
            accountRepository.save(currentAccount);
            return "Transaction is created";
           //restrict transactions only to savings account
            //restrict only negative transactions to other accounts
        } else return "customer Account not found";
    }

    @RequestMapping("/search/{id}")
    public String search(@PathVariable long id) {
        return repository.findById(id).toString();
    }

    @RequestMapping("/searchByCustId/{id}")
    public List<Transaction> fetchDataByTransactionName(@PathVariable long id) {
        Pageable sortedByTransactionIdDesc =
                PageRequest.of(0, 10, Sort.by("transactionId").descending());
        return repository.findAllByCustomerId(id, sortedByTransactionIdDesc);
    }
}
