package com.spin.transaction.controller;

import com.spin.transaction.dto.TransactionRequest;
import com.spin.transaction.dto.TransactionResponse;
import com.spin.transaction.entity.Transaction;
import com.spin.transaction.numbregeneratorservice.TransactionType;
import com.spin.transaction.repository.TransactionRepository;
import com.spin.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules();
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionServices;

    @Test
    void createTransaction_shouldPersistAndReturnResponse() throws Exception {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId("acc-123");
        request.setAmount(new BigDecimal("1500"));
        request.setCurrency("MXN");
        request.setDescription("ESTA ES UNA PRUEBA DE SPRINGBOOTTEST");
        request.setType(TransactionType.CREDIT);

        // 🔥 EJECUTA TODO EL FLUJO: Controller → Service → Repository → DB
        String responseJson = mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").value("acc-123"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // 🔥 CONVIERTE RESPUESTA
        TransactionResponse response =
                objectMapper.readValue(responseJson, TransactionResponse.class);

        // 🔥 VERIFICACIÓN EN BD REAL
        Transaction saved = transactionRepository.findById(response.getId())
                .orElseThrow();

        assertEquals("acc-123", saved.getAccountId());
        assertEquals(new BigDecimal("1500.00"), saved.getAmount());
    }
}
