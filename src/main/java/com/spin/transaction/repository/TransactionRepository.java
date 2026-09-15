package com.spin.transaction.repository;

import com.spin.transaction.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository <Transaction, Long> {
}
