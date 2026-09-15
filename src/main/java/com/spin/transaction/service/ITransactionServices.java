package com.spin.transaction.service;

import dto.TransactionRequest;
import dto.TransactionResponse;

import java.util.List;
import java.util.Optional;

public interface ITransactionServices {

    public TransactionResponse create(TransactionRequest transactionRequest);

}

