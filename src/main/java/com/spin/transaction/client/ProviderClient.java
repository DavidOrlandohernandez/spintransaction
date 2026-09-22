package com.spin.transaction.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spin.transaction.dto.provider.ProviderRequest;
import com.spin.transaction.dto.provider.ProviderResponse;
import com.spin.transaction.enums.TransactionStatus;
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
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
@Component
public class ProviderClient  implements TransactionExecutor{

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

    @CircuitBreaker(name = "providerService", fallbackMethod = "fallback")
    @Override
    public ProviderResponse execute(ProviderRequest request) {

        String url = providerUrl;

        try {

            log.info("Iniciando llamado de proveedor accountId: : {},{}", request.getAccountId(),providerUrl);
            return restTemplate.postForObject(
                    url,
                    request,
                    ProviderResponse.class
            );

        }  catch (HttpClientErrorException | HttpServerErrorException ex) {

            String body = ex.getResponseBodyAsString();
            log.error("RAW provider error: {}", body);

            ProviderErrorResponse error = null;

            try {
                error = objectMapper.readValue(body, ProviderErrorResponse.class);
            } catch (Exception parseException) {
                log.warn("No se pudo mapear error del provider");
            }

            String code = (error != null) ? error.getCode() : "UNKNOWN_ERROR";
            String status = (error != null) ? error.getStatus() : TransactionStatus.REJECTED.toString();

            String message = (error != null) ? error.getMessage() : body;

            throw new ProviderException(
                    code,
                    status,
                    message,
                    ex.getStatusCode().value() + ""
            );
        }
    }

    public ProviderResponse fallback(ProviderRequest request, Throwable ex) {

        log.error("Fallback ejecutado para accountId: {}", request.getAccountId());

        if (ex instanceof ProviderException providerEx) {

            log.error("ProviderException code: {}, status: {}",
                    providerEx.getCode(),
                    providerEx.getStatus());

            throw new ProviderException(
                    providerEx.getCode(),
                    providerEx.getStatus(),
                    providerEx.getMessage(),
                    providerEx.getHttpStatus()
            );
        }

        // 🔥 fallback genérico (circuit breaker, timeout, etc)
        throw new ProviderException(
                "REJECTED",
                "SERVICE_UNAVAILABLE",
                "Provider is currently unavailable",
                "503"
        );
    }
}