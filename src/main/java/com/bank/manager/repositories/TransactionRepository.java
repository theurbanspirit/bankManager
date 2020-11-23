package com.bank.manager.repositories;

import com.bank.manager.models.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
    //    List<Transaction> findAllByCustomerId(long customerId, Pageable pageable);
    List<Transaction> findAllByCustomerId(long customerId, Pageable pageable);
}
