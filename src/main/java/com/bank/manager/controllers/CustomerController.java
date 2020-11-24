package com.bank.manager.controllers;

import com.bank.manager.enums.Role;
import com.bank.manager.models.AppUser;
import com.bank.manager.models.Customer;
import com.bank.manager.repositories.AccountRepository;
import com.bank.manager.repositories.AppUserRepository;
import com.bank.manager.repositories.CustomerRepository;
import com.bank.manager.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
@RequestMapping("/bankManager/customer")
public class CustomerController {
    @Autowired
    CustomerRepository repository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    AppUserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final String passwordPostFix = ".123";

    private String createPassword(String username) {
        return bCryptPasswordEncoder.encode(String.join("", username, passwordPostFix));
    }

    @PostMapping("/create")
    public String create(@RequestBody Customer customer) {
        log.info("Loggedin user name is {} ", SecurityContextHolder.getContext().getAuthentication().getName());
        long contactEmployeeId = employeeRepository.findByEmployeeName(
                SecurityContextHolder.getContext().getAuthentication().getName())
                .getEmployeeId();
        repository.save(new Customer(customer.getCustomerName(), customer.getCustomerAddress(), customer.getCustomerEmail(), contactEmployeeId));
        if (userRepository.existsByUsername(customer.getCustomerName())) {
            return "username already exists please provide another variation of the name";
        }
        userRepository.save(new AppUser(customer.getCustomerName(), createPassword(customer.getCustomerName()), Role.CUSTOMER));
        return customer.toString();
    }

    @RequestMapping("/search/{id}")
    public String search(@PathVariable long id) {
        return repository.findById(id).toString();
    }

    @RequestMapping("/searchByName/{name}")
    public Customer fetchDataByEmployeeName(@PathVariable String name) {
        return repository.findByCustomerName(name);
    }

    @PostMapping("/updateKyc/{id}")
    public String updateCustomerKyc(@PathVariable long id, @RequestBody Customer customer) {
        if (userRepository.existsByUsername(customer.getCustomerName())) {
            return "username already exists please another variation of the name";
        }
        if (repository.existsById(id)) {
            AppUser oldUser = userRepository.findByUsername(repository.findByCustomerId(id).getCustomerName());
            oldUser.setUsername(customer.getCustomerName());
            oldUser.setPassword(createPassword(customer.getCustomerName()));
            userRepository.save(oldUser);
            repository.save(customer);
        }
        return "updated customer kyc";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable long id) {
        if (!repository.existsById(id))
            return "Sorry, customer not found. Please try another id";
        //delete customer
        repository.deleteById(id);
        //delete user
        userRepository.deleteByUsername(repository.findByCustomerId(id).getCustomerName());
        //delete accounts
        accountRepository.deleteByCustomerId(id);
        return "customer deleted successfully";
    }

}
