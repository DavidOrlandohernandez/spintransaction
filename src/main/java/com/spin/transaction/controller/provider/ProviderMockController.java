package com.spin.transaction.controller.provider;

import com.spin.transaction.dto.ProviderRequest;
import com.spin.transaction.dto.ProviderResponse;
import com.spin.transaction.numbregeneratorservice.TransactionStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/provider/v1")
public class ProviderMockController {

    @PostMapping("/execute")
    public ProviderResponse execute(@RequestBody ProviderRequest request) {

        if (request.getAmount().doubleValue() > 20000) {
            throw new RuntimeException("INSUFFICIENT_FUNDS");
        }

        ProviderResponse response = new ProviderResponse();
        response.setTransactionId(UUID.randomUUID().toString());
        response.setStatus(TransactionStatus.APPROVED);
        response.setBalance(new BigDecimal("5500"));
        response.setExecutedAt(Instant.now());

        return response;
    }
}