package com.spin.transaction.service;

import com.spin.transaction.dto.TransactionRequest;
import com.spin.transaction.dto.TransactionResponse;
import com.spin.transaction.entity.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITransactionServices {

    public TransactionResponse create(TransactionRequest transactionRequest);

    List<TransactionResponse> findAll();

    TransactionResponse findTransactionById(UUID id);

}

