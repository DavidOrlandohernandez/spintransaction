package com.spin.transaction.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spin.transaction.dto.provider.ProviderRequest;
import com.spin.transaction.dto.provider.ProviderResponse;
import com.spin.transaction.exception.ProviderException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import com.spin.transaction.exception.model.ProviderErrorResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ProviderClient {

    @Value("${provider.url}")
    private String providerUrl;

    private static final Logger log = LoggerFactory.getLogger(ProviderClient.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProviderClient() {
        SimpleClientHttpRequestFactory factory =
                new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        this.restTemplate = new RestTemplate(factory);
    }

    public ProviderResponse execute(ProviderRequest request) {

        String url = providerUrl;

        try {

            log.info("Iniciando llamado de proveedor accountId: : {},{}", request.getAccountId(),providerUrl);
            return restTemplate.postForObject(
                    url,
                    request,
                    ProviderResponse.class
            );

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            try {

                log.info("Error inesperado de proveedor: : {}", request.getAccountId());
                ProviderErrorResponse error =
                        objectMapper.readValue(
                                ex.getResponseBodyAsString(),
                                ProviderErrorResponse.class
                        );

                log.info("Error inesperado de proveedor:{},{},{}", error.getMessage(), error.getStatus(), error.getCode());
                throw new ProviderException(
                        error.getStatus(),
                        error.getCode(),
                        error.getMessage()
                );

            } catch (Exception parseException) {
                throw new ProviderException(
                        "400",
                        "UNKNOWN_ERROR",
                        "Error calling provider"
                );
            }
        }
    }
}