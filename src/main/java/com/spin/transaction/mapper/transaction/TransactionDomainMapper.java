package com.spin.transaction.mapper.transaction;

import com.spin.transaction.domain.model.TransactionDomain;
import com.spin.transaction.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionDomainMapper {

    TransactionDomainMapper INSTANCE = Mappers.getMapper(TransactionDomainMapper.class);

    /*Mapper de la clase Transaction Objeto por Objeto One to One; */
    @Named("defaultMapping")
    Transaction transactionDomainToTransaction(TransactionDomain transaction);
}