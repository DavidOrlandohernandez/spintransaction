package com.spin.transaction.repository;

import com.spin.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, UUID>,
                JpaSpecificationExecutor<Transaction> {

    Optional<Transaction> findByIdempotencyKey(String idempotencyKey);
}
