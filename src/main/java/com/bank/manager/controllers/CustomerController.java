package com.bank.manager.controllers;

import com.bank.manager.enums.Role;
import com.bank.manager.models.AppUser;
import com.bank.manager.models.Customer;
import com.bank.manager.repositories.AppUserRepository;
import com.bank.manager.repositories.CustomerRepository;
import com.bank.manager.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bankManager/customer")
public class CustomerController {
    @Autowired
    CustomerRepository repository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    AppUserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final String  passwordPostFix = ".123";


    @PostMapping("/create")
    public String create(@RequestBody Customer customer) {
        log.info("Loggedin user name is {} ", SecurityContextHolder.getContext().getAuthentication().getName());
        long contactEmployeeId = employeeRepository.findByEmployeeName(
                SecurityContextHolder.getContext().getAuthentication().getName())
                .getEmployeeId();
        repository.save(new Customer(customer.getCustomerName(), customer.getCustomerAddress(), customer.getCustomerEmail(), contactEmployeeId));
        userRepository.save(new AppUser(customer.getCustomerName(), createPassword(customer.getCustomerName()), Role.CUSTOMER));
        //also save to userRepository
        return "Customer is created";
    }

    @GetMapping("/getAuthority")
    public String grantedAuthoritySet(Authentication authentication) {
        log.info("Loggedin authorities is {} ", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return authentication.getName();
    }

    @RequestMapping("/search/{id}")
    public String search(@PathVariable long id) {
        return repository.findById(id).toString();
    }

    @RequestMapping("/searchByName/{name}")
    public List<Customer> fetchDataByEmployeeName(@PathVariable String name) {
        return repository.findByCustomerName(name);
    }

    public String createPassword(String username) {

        return bCryptPasswordEncoder.encode(String.join("", username, passwordPostFix));
    }
}
