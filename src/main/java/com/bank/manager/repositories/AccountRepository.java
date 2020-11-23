package com.bank.manager.repositories;

import com.bank.manager.models.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByCustomerId(long customerId);

    Account findByAccountId(long accountId);
}
