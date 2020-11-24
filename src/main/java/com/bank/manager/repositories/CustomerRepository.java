package com.bank.manager.repositories;

import com.bank.manager.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByCustomerName(String customerName);

    boolean existsByCustomerName(String customerName);

    List<Customer> findByContactEmployeeId(long contactEmployeeId);

    Customer findByCustomerId(long customerId);


}
