package com.bank.manager.repositories;

import com.bank.manager.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByCustomerName(String customerName);

    List<Customer> findByContactEmployeeId(long contactEmployeeId);

}
