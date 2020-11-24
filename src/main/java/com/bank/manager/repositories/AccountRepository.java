package com.bank.manager.repositories;

import com.bank.manager.enums.AccountType;
import com.bank.manager.models.Account;
import com.bank.manager.models.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByCustomerId(long customerId);

    @Query("select case when count(a)> 0 then true else false end from Account a where a.customerId = :customerId and lower(a.accountType) =  lower(:accountType)")
    boolean existsCustomerIdByAccountType(@Param("customerId") long customerId, @Param("accountType") String accountType);

    @Query("select a from Account a where a.customerId = :customerId and lower(a.accountType) =  lower(:accountType)")
    Account findCustomerAccountByAccountType(@Param("customerId") long customerId, @Param("accountType") String accountType);

    Account findByAccountId(long accountId);

    void deleteByCustomerId(long customerId);
}
