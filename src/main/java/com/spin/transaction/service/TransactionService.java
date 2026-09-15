package com.spin.transaction.service;

import com.spin.transaction.client.ProviderClient;
import com.spin.transaction.dto.ProviderRequest;
import com.spin.transaction.dto.ProviderResponse;
import com.spin.transaction.entity.Transaction;
import com.spin.transaction.exception.ProviderException;
import com.spin.transaction.numbregeneratorservice.TransactionStatus;
import com.spin.transaction.numbregeneratorservice.TransactionType;
import com.spin.transaction.dto.TransactionRequest;
import com.spin.transaction.dto.TransactionResponse;
import com.spin.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

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

        //1.- Validaciones de negocio.
        validator.validate(transactionRequest);

        //2.- Crear entidad base
        Transaction transaction = new Transaction();

        //transaction.setId(UUID.randomUUID());//Tal vez no debe de generarse aqui debido a que la clase ya tiene el suyo.
        transaction.setAccountId(transactionRequest.getAccountId()); //LO CONTIENE EL REQUEST
        transaction.setType(transactionRequest.getType());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setCurrency(transactionRequest.getCurrency());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setCreatedAt(OffsetDateTime.now());

        try{

            //3.- Construcción de ProviderRequest
            ProviderRequest providerRequest = new ProviderRequest();
            providerRequest.setAccountId(transactionRequest.getAccountId());
            providerRequest.setType(transactionRequest.getType());
            providerRequest.setAmount(transactionRequest.getAmount());

            //4.- LLamado de provider
            ProviderResponse providerResponse = providerClient.execute(providerRequest);

            //5.- Escenario exitoso
            transaction.setStatus(providerResponse.getStatus());
            transaction.setProviderTransactionId(providerResponse.getTransactionId());
            transaction.setBalanceAfter(providerResponse.getBalance());

        }catch (ProviderException ex){
            // 6. Escenario fallido
            transaction.setStatus(TransactionStatus.REJECTED);
            transaction.setProviderTransactionId(null);
            transaction.setBalanceAfter(null);
        }

        Transaction transactionSaved = transactionRepository.save(transaction);

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

        return  response;
    }
}
