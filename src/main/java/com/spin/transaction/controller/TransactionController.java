package com.spin.transaction.controller;

import com.spin.transaction.service.ITransactionServices;
import com.spin.transaction.dto.TransactionRequest;
import com.spin.transaction.dto.TransactionResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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
                .created(URI.create("/api/v1/transactions/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<?> findTransaction() {
        List<TransactionResponse> response = transactionServices.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTransactionById(@PathVariable UUID id) {
        TransactionResponse transaction = transactionServices.findTransactionById(id);
        return ResponseEntity.ok(transaction);
    }
}
