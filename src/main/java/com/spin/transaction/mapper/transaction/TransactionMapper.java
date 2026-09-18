package com.spin.transaction.mapper.transaction;

import com.spin.transaction.dto.transaction.TransactionResponse;
import com.spin.transaction.entity.Transaction;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    /*Mapper de la clase Transaction Objeto por Objeto One to One; */
    @Named("defaultMapping")
    TransactionResponse transactionToTransactionResponse(Transaction transaction);

    @IterableMapping(qualifiedByName = "defaultMapping")
    List<TransactionResponse> transactionList(List<Transaction> transaction);

}
