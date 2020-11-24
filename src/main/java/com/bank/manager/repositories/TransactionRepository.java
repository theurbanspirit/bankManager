package com.bank.manager.repositories;

import com.bank.manager.models.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
    //    List<Transaction> findAllByCustomerId(long customerId, Pageable pageable);
    List<Transaction> findAllByCustomerId(long customerId, Pageable pageable);

    @Query("select t from Transaction t where t.date >= :fromDate and t.date<=:toDate and t.accountId=:accountId order by date desc")
    List<Transaction> getAccountStatement(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("accountId") long customerId);
}
