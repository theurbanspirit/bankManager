package com.bank.manager.controllers;

import com.bank.manager.enums.AccountType;
import com.bank.manager.models.Account;
import com.bank.manager.models.Customer;
import com.bank.manager.models.Transaction;
import com.bank.manager.repositories.AccountRepository;
import com.bank.manager.repositories.CustomerRepository;
import com.bank.manager.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create")
    public String create(@RequestBody Transaction transaction, Authentication authentication) {
        if (accountRepository.findCustomerAccountByAccountType(customerRepository.findByCustomerName(authentication.getName()).getCustomerId(), AccountType.savings.toString())==null) {
            return "customer Savings Account not found";
        }
        Account account = accountRepository.findCustomerAccountByAccountType(customerRepository.findByCustomerName(authentication.getName()).getCustomerId(), AccountType.savings.toString());
        if (account.getAccountBalance() + transaction.getValue() < 0)
                return "Sorry, insufficient balance";
            repository.save(new Transaction(transaction.getValue(), transaction.getCustomerId(), transaction.getAccountId()));
            account.setAccountBalance(account.getAccountBalance() + transaction.getValue());
            accountRepository.save(account);
            return "Transaction is created";
            //restrict transactions only to savings account
    }

    @PreAuthorize("permitAll()")
    @RequestMapping("/search/{id}")
    public String search(@PathVariable long id) {
        return repository.findById(id).toString();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @RequestMapping("/getLastTen")
    public List<Transaction> fetchDataByTransactionName(Authentication authentication) {
        Pageable sortedByTransactionIdDesc =
                PageRequest.of(0, 10, Sort.by("transactionId").descending());
        return repository.findAllByCustomerId(customerRepository.findByCustomerName(authentication.getName()).getCustomerId(), sortedByTransactionIdDesc);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @RequestMapping("/transfer/{toAccount}")
    public String transferToAccount(@PathVariable String toAccount, @RequestBody float amount, Authentication authentication) {
        //search if account exists
        if (!customerRepository.existsByCustomerName(toAccount)) {
            return "Receiver doesn't exist";
        }
        if (amount < 0)
            return "Please provide positive amount to transfer";
        String senderName = authentication.getName();
        Customer sender = customerRepository.findByCustomerName(senderName);
        Customer receiver = customerRepository.findByCustomerName(toAccount);
        Account senderAcct = accountRepository.findCustomerAccountByAccountType(sender.getCustomerId(), AccountType.savings.toString());
        Account receiverAcct = accountRepository.findCustomerAccountByAccountType(receiver.getCustomerId(), AccountType.savings.toString());
        if (senderAcct.getAccountBalance() - amount < 0)
            return "Sorry, insufficient Balance. Transfer restricted";
        //sender transaction
        create(new Transaction(amount * -1, senderAcct.getCustomerId(), senderAcct.getAccountId()), authentication);
        //receiver transaction
        create(new Transaction(amount, receiverAcct.getCustomerId(), receiverAcct.getAccountId()), authentication);

        return String.format("%f transferred to %s . New balance is %f", amount, toAccount, senderAcct.getAccountBalance());
    }
}
