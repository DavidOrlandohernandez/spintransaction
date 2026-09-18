package com.spin.transaction.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;


import com.spin.transaction.client.ProviderClient;
import com.spin.transaction.dto.provider.ProviderResponse;
import com.spin.transaction.dto.transaction.TransactionRequest;
import com.spin.transaction.dto.transaction.TransactionResponse;
import com.spin.transaction.entity.Transaction;
import com.spin.transaction.exception.ProviderException;
import com.spin.transaction.exception.ResourceNotFoundException;
import com.spin.transaction.mapper.provider.ProviderMapper;
import com.spin.transaction.mapper.transaction.TransactionMapper;
import com.spin.transaction.enums.TransactionStatus;
import com.spin.transaction.enums.TransactionType;
import com.spin.transaction.repository.TransactionRepository;
import com.spin.transaction.service.rules.TransactionBusinessRules;
import com.spin.transaction.specification.TransactionSpecification;
import com.spin.transaction.wraper.Meta;
import com.spin.transaction.wraper.PageResponse;

@Service
public class TransactionServiceImpl implements ITransactionService {

    private static final Logger log =
            LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionBusinessRules validator;
    private final ProviderClient providerClient;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionBusinessRules validator, ProviderClient providerClient,
                                  TransactionRepository transactionRepository) {
        this.validator = validator;
        this.providerClient = providerClient;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionResponse create(TransactionRequest transactionRequest) {

        log.info("Iniciando validación de negocio accountId: {}", transactionRequest.getAccountId());
        validator.validate(transactionRequest);

        log.info("Iniciando creación de Transaction accountId: {}", transactionRequest.getAccountId());
        Transaction transaction = new Transaction();
        transaction.setAccountId(transactionRequest.getAccountId());
        transaction.setType(transactionRequest.getType());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setCurrency(transactionRequest.getCurrency());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setCreatedAt(OffsetDateTime.now());

        try{

            log.info("Iniciando llamado de proveedor accountId: : {}", transactionRequest.getAccountId());
            ProviderResponse providerResponse = providerClient.execute(ProviderMapper.
                    INSTANCE.transactionRequestToProviderRequest(transactionRequest));
            log.info("Finaliza llamado de proveedor accountId: : {}", providerResponse.getTransactionId());

            transaction.setStatus(providerResponse.getStatus());
            transaction.setProviderTransactionId(providerResponse.getTransactionId());
            transaction.setBalanceAfter(providerResponse.getBalance());

        }catch (ProviderException ex){

            transaction.setStatus(TransactionStatus.REJECTED);
            transaction.setProviderTransactionId(null);
            transaction.setBalanceAfter(null);
        }

        log.info("Iniciando persistencia de transacción accountId: : {}", transactionRequest.getAccountId());
        Transaction transactionSaved = transactionRepository.save(transaction);
        log.info("Finaliza persistencia de transacción id: : {}", transactionSaved.getId());

        return TransactionMapper.
                INSTANCE.transactionToTransactionResponse(transactionSaved);
    }

    @Override
    public List<TransactionResponse> findAll() {

       List<Transaction> transactionList =
               transactionRepository.findAll();

       return TransactionMapper.
                INSTANCE.transactionList(transactionList);

    }

    @Override
    public TransactionResponse findTransactionById(UUID id) {

        Transaction transaction = transactionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + id));

         return TransactionMapper.
            INSTANCE.transactionToTransactionResponse(transaction);
    }

    @Override
    public PageResponse<TransactionResponse> findTransactions(
            String accountId,
            TransactionStatus status,
            TransactionType type,
            int page,
            int limit
    ) {
        log.info("Iniciando consulta de transacción accountId: {}", accountId);
        Pageable pageable = PageRequest.of(page, limit, Sort.by("createdAt").descending());

        log.info("Construcción de consulta por filtro accountId: {}", accountId);
        Specification<Transaction> spec =
                TransactionSpecification.filter(accountId, status, type);

        log.info("Obtención de transacción por filtro y paginación accountId: {}", accountId);
        Page<Transaction> transactions =
                transactionRepository.findAll(spec, pageable);

        List<TransactionResponse> data = TransactionMapper.INSTANCE
                .transactionList(transactions.getContent());

        Meta meta = new Meta(
                page,
                limit,
                transactions.getTotalElements()
        );

        log.info("Finalizando consulta de transacción accountId: {}", accountId);
        return new PageResponse<>(data, meta);
    }
}
