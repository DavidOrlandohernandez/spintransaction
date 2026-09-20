package com.spin.transaction.service;

import com.spin.transaction.dto.transaction.TransactionRequest;
import com.spin.transaction.dto.transaction.TransactionResponse;
import com.spin.transaction.enums.TransactionStatus;
import com.spin.transaction.enums.TransactionType;
import com.spin.transaction.wraper.PageResponse;

import java.util.List;
import java.util.UUID;

public interface ITransactionService {

    public TransactionResponse create(TransactionRequest transactionRequest, String idempotencyKey);

    List<TransactionResponse> findAll();

    TransactionResponse findTransactionById(UUID id);

    public PageResponse<TransactionResponse> findTransactions(
            String accountId,
            TransactionStatus status,
            TransactionType type,
            int page,
            int limit
    );
}

