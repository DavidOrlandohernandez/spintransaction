package com.spin.transaction.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spin.transaction.dto.ProviderRequest;
import com.spin.transaction.dto.ProviderResponse;
import com.spin.transaction.exception.ProviderException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import com.spin.transaction.exception.ProviderErrorResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@Component
public class ProviderClient {

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

        String url = "http://localhost:8085/provider/v1/execute";

        try {

            return restTemplate.postForObject(
                    url,
                    request,
                    ProviderResponse.class
            );

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            try {
                ProviderErrorResponse error =
                        objectMapper.readValue(
                                ex.getResponseBodyAsString(),
                                ProviderErrorResponse.class
                        );
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