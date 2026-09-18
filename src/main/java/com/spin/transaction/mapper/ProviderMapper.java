package com.spin.transaction.mapper;

import com.spin.transaction.dto.ProviderRequest;
import com.spin.transaction.dto.TransactionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProviderMapper {

    ProviderMapper INSTANCE = Mappers.getMapper(ProviderMapper.class);

    /*Mapper de la clase TransactionRequest Objeto por Objeto One to One; */
    @Named("defaultMapping")
    ProviderRequest transactionRequestToProviderRequest(TransactionRequest transactionRequest);

}
