package com.spin.transaction.service;

import com.spin.transaction.client.ProviderClient;
import com.spin.transaction.dto.ProviderRequest;
import com.spin.transaction.dto.ProviderResponse;
import com.spin.transaction.entity.Transaction;
import com.spin.transaction.exception.ProviderException;
import com.spin.transaction.exception.ResourceNotFoundException;
import com.spin.transaction.numbregeneratorservice.TransactionStatus;
import com.spin.transaction.dto.TransactionRequest;
import com.spin.transaction.dto.TransactionResponse;
import com.spin.transaction.numbregeneratorservice.TransactionType;
import com.spin.transaction.repository.TransactionRepository;
import com.spin.transaction.specification.TransactionSpecification;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.domain.Specification;

@Service
public class TransactionService implements  ITransactionServices{

    private final BusinessRulesValidator validator;
    private final ProviderClient providerClient;
    private final TransactionRepository transactionRepository;

    public TransactionService(BusinessRulesValidator validator, ProviderClient providerClient,
                              TransactionRepository transactionRepository) {
        this.validator = validator;
        this.providerClient = providerClient;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionResponse create(TransactionRequest transactionRequest) {

        validator.validate(transactionRequest);

        Transaction transaction = new Transaction();
        transaction.setAccountId(transactionRequest.getAccountId());
        transaction.setType(transactionRequest.getType());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setCurrency(transactionRequest.getCurrency());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setCreatedAt(OffsetDateTime.now());

        try{

            ProviderRequest providerRequest = new ProviderRequest();
            providerRequest.setAccountId(transactionRequest.getAccountId());
            providerRequest.setType(transactionRequest.getType());
            providerRequest.setAmount(transactionRequest.getAmount());

            ProviderResponse providerResponse = providerClient.execute(providerRequest);

            transaction.setStatus(providerResponse.getStatus());
            transaction.setProviderTransactionId(providerResponse.getTransactionId());
            transaction.setBalanceAfter(providerResponse.getBalance());

        }catch (ProviderException ex){
            transaction.setStatus(TransactionStatus.REJECTED);
            transaction.setProviderTransactionId(null);
            transaction.setBalanceAfter(null);
        }

        Transaction transactionSaved = transactionRepository.save(transaction);

        TransactionResponse response = getTransactionResponse(transactionSaved);

        return  response;
    }

    private static TransactionResponse getTransactionResponse(Transaction transactionSaved) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transactionSaved.getId());
        response.setAccountId(transactionSaved.getAccountId());
        response.setType(transactionSaved.getType());
        response.setAmount(transactionSaved.getAmount());
        response.setCurrency(transactionSaved.getCurrency());
        response.setDescription(transactionSaved.getDescription());
        response.setStatus(transactionSaved.getStatus());
        response.setProviderTransactionId(transactionSaved.getProviderTransactionId());
        response.setBalanceAfter(transactionSaved.getBalanceAfter());
        response.setCreatedAt(transactionSaved.getCreatedAt());
        return response;
    }

    @Override
    public List<TransactionResponse> findAll() {

       List<Transaction> transactionList =
               transactionRepository.findAll();

        return transactionList
                 .stream()
                 .map(transaction -> TransactionResponse.builder()
                         .id(transaction.getId())
                         .accountId(transaction.getAccountId())
                         .type(transaction.getType())
                         .amount(transaction.getAmount())
                         .currency(transaction.getCurrency())
                         .description(transaction.getDescription())
                         .status(transaction.getStatus())
                         .providerTransactionId(transaction.getProviderTransactionId())
                         .balanceAfter(transaction.getBalanceAfter())
                         .createdAt(transaction.getCreatedAt())
                         .build()).
                 toList();
    }

    @Override
    public TransactionResponse findTransactionById(UUID id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + id));

        return TransactionResponse
              .builder()
                  .id(transaction.getId())
                  .accountId(transaction.getAccountId())
                  .type(transaction.getType())
                  .amount(transaction.getAmount())
                  .currency(transaction.getCurrency())
                  .description(transaction.getDescription())
                  .status(transaction.getStatus())
                  .providerTransactionId(transaction.getProviderTransactionId())
                  .balanceAfter(transaction.getBalanceAfter())
                  .createdAt(transaction.getCreatedAt())
              .build();
        }


    @Override
    public Page<TransactionResponse> findTransactions(
            String accountId,
            TransactionStatus status,
            TransactionType type,
            int page,
            int limit
    ) {

        Pageable pageable = PageRequest.of(page, limit, Sort.by("createdAt").descending());

        Specification<Transaction> spec =
                TransactionSpecification.filter(accountId, status, type);

        Page<Transaction> transactions =
                transactionRepository.findAll(spec, pageable);

        return transactions.map(this::mapToResponse);
    }

    private TransactionResponse mapToResponse(Transaction transaction) {

        return TransactionResponse.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccountId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .description(transaction.getDescription())
                .status(transaction.getStatus())
                .providerTransactionId(transaction.getProviderTransactionId())
                .balanceAfter(transaction.getBalanceAfter())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
