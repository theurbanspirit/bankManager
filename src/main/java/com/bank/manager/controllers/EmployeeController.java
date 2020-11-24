package com.bank.manager.controllers;

import com.bank.manager.enums.Role;
import com.bank.manager.models.AppUser;
import com.bank.manager.models.Employee;
import com.bank.manager.repositories.AppUserRepository;
import com.bank.manager.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bankManager/employee")
public class EmployeeController {
    @Autowired
    EmployeeRepository repository;
    @Autowired
    AppUserRepository userRepository;

    private final String passwordPostFix = ".123";

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String create(@RequestBody Employee employee) {
        repository.save(new Employee(employee.getEmployeeName(), employee.getEmployeeAddress(), employee.getEmployeeEmail(), employee.getEmployeeType()));
        userRepository.save(new AppUser(employee.getEmployeeName(), createPassword(employee.getEmployeeName()), employee.getEmployeeType()));
        //also save to user repository
        return ("Employee is created");
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @RequestMapping("/search/{id}")
    public String search(@PathVariable long id) {
        return repository.findById(id).toString();
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @RequestMapping("/searchByName/{name}")
    public Employee fetchDataByEmployeeName(@PathVariable String name) {
        return repository.findByEmployeeName(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable long id) {
        if (!userRepository.existsById(id))
            return "Employee doesn't exist for given id. Try another id";
        repository.deleteById(id);
        userRepository.deleteByUsername(repository.findByEmployeeId(id).getEmployeeName());
        return "Employee deleted";
    }

    //please don't use this method except to create the first admin
    @PreAuthorize("permitAll()")
    @PostMapping("/createFirstAdmin")
    public String createFirstAdmin(@RequestBody Employee employee){
        repository.save(new Employee(employee.getEmployeeName(), employee.getEmployeeAddress(), employee.getEmployeeEmail(), Role.ADMIN));
        userRepository.save(new AppUser(employee.getEmployeeName(), createPassword(employee.getEmployeeName()), Role.ADMIN));
        return "First Admin Created";
    }
    public String createPassword(String username) {
        return bCryptPasswordEncoder.encode(String.join("", username, passwordPostFix));
    }
}
