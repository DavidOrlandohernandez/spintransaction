package com.spin.transaction.controller;

import com.spin.transaction.service.ITransactionServices;
import dto.TransactionRequest;
import dto.TransactionResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transaction - Controller",
        description = "API REST que gestiona la ejecución de transacciones financieras (crédito y débito).")
public class TransactionController {

    @Autowired
    private ITransactionServices transactionServices;

    @PostMapping
    public ResponseEntity<TransactionResponse> create(
            @Valid @RequestBody TransactionRequest transactionRequest) {

        TransactionResponse response = transactionServices.create(transactionRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
