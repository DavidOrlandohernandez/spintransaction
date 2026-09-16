package com.spin.transaction.service;

import com.spin.transaction.dto.TransactionRequest;
import com.spin.transaction.dto.TransactionResponse;
import com.spin.transaction.entity.Transaction;
import com.spin.transaction.numbregeneratorservice.TransactionStatus;
import com.spin.transaction.numbregeneratorservice.TransactionType;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITransactionServices {

    public TransactionResponse create(TransactionRequest transactionRequest);

    List<TransactionResponse> findAll();

    TransactionResponse findTransactionById(UUID id);

    public Page<TransactionResponse> findTransactions(
            String accountId,
            TransactionStatus status,
            TransactionType type,
            int page,
            int limit
    );
}

