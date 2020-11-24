package com.bank.manager.repositories;

import com.bank.manager.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Employee findByEmployeeName(String employeeName);

    Employee findByEmployeeId(long id);

}
