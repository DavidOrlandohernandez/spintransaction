package com.spin.transaction.controller.provider;

import com.spin.transaction.dto.ProviderRequest;
import com.spin.transaction.dto.ProviderResponse;
import com.spin.transaction.numbregeneratorservice.TransactionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/provider/v1")
public class ProviderMockController {

    @PostMapping("/execute")
    public ResponseEntity<?> execute(@RequestBody ProviderRequest request) {

        if (request.getAmount().doubleValue() > 20000) {

            Map<String, Object> error = new HashMap<>();
            error.put("status", "REJECTED");
            error.put("code", "INSUFFICIENT_FUNDS");
            error.put("message", "The account does not have enough balance to complete the transaction");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        ProviderResponse response = new ProviderResponse();
        response.setTransactionId(UUID.randomUUID().toString());
        response.setStatus(TransactionStatus.APPROVED);
        response.setBalance(new BigDecimal("5500"));
        response.setExecutedAt(Instant.now());

        return ResponseEntity.ok(response);
    }
}