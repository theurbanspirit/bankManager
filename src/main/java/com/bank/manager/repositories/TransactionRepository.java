package com.bank.manager.repositories;

import com.bank.manager.models.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
    //    List<Transaction> findAllByCustomerId(long customerId, Pageable pageable);
    List<Transaction> findAllByCustomerId(long customerId, Pageable pageable);

    @Query("select t from transaction t where date >= :fromDate and date<=:toDate and customerId=:customerId and accountType='savings' order by date desc")
    List<Transaction> getAccountStatement(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("customerId") long customerId );
}
