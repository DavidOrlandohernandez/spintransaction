package com.spin.transaction.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.spin.transaction.client.TransactionExecutor;
import com.spin.transaction.domain.model.TransactionDomain;
import com.spin.transaction.exception.NotIdempotencyException;
import com.spin.transaction.mapper.transaction.TransactionDomainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

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
import com.spin.transaction.specification.TransactionSpecification;
import com.spin.transaction.wraper.Meta;
import com.spin.transaction.wraper.PageResponse;

@Service
public class TransactionServiceImpl implements ITransactionService {

    private static final Logger log =
            LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionExecutor transactionExecutor;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionExecutor transactionExecutor,
                                  TransactionRepository transactionRepository) {
        this.transactionExecutor = transactionExecutor;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionResponse create(TransactionRequest request,String idempotencyKey) {

        log.info("Validando request accountId: {}", request.getAccountId());
        TransactionDomain domain = new TransactionDomain(
                request.getAccountId(),
                request.getType(),
                request.getAmount(),
                request.getCurrency(),
                request.getDescription()
        );

        domain.applyBusinessRules();

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new NotIdempotencyException("Idempotency-Key is required");
        }

        Optional<Transaction> existing =
                transactionRepository.findByIdempotencyKey(idempotencyKey);

        if (existing.isPresent()) {
            log.info("Transacción duplicada, devolviendo resultado previo");
            return TransactionMapper.INSTANCE.transactionToTransactionResponse(existing.get());
        }

        try {

            log.info("Llamando proveedor accountId: {}", request.getAccountId());
            ProviderResponse response = transactionExecutor.execute(
                    ProviderMapper.INSTANCE.transactionRequestToProviderRequest(request)
            );

            log.warn("Proveedor acepto transacción accountId: {}", request.getAccountId());
            domain.markAsExecuted(
                    String.valueOf(response.getStatus()),
                    response.getTransactionId(),
                    response.getBalance()
            );

        } catch (ProviderException ex) {
            log.warn("Proveedor rechazó transacción accountId: {}", request.getAccountId());
            domain.markAsRejected(
                    ex.getCode(),
                    ex.getStatus(),
                    ex.getMessage(),
                    ex.getHttpStatus()
            );
        }

        Transaction entity = TransactionDomainMapper.INSTANCE.transactionDomainToTransaction(domain);

        log.info("Persistiendo transacción accountId: {}", request.getAccountId());
        entity.setIdempotencyKey(idempotencyKey);
        Transaction saved = transactionRepository.save(entity);

        return TransactionMapper.INSTANCE.transactionToTransactionResponse(saved);
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
