package com.spin.transaction.service;

import com.spin.transaction.dto.TransactionRequest;
import com.spin.transaction.dto.TransactionResponse;

import java.util.List;
import java.util.Optional;

public interface ITransactionServices {

    public TransactionResponse create(TransactionRequest transactionRequest);

}

