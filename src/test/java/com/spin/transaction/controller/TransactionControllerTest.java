package com.spin.transaction.controller;

import com.spin.transaction.dto.transaction.TransactionRequest;
import com.spin.transaction.dto.transaction.TransactionResponse;
import com.spin.transaction.entity.Transaction;
import com.spin.transaction.exception.model.ErrorResponse;
import com.spin.transaction.enums.TransactionStatus;
import com.spin.transaction.enums.TransactionType;
import com.spin.transaction.repository.TransactionRepository;
import com.spin.transaction.service.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.UUID;

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
    private TransactionServiceImpl transactionServices;

    @Test
    void createTransaction_TransactionStatusApproved() throws Exception {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId("acc-123");
        request.setAmount(new BigDecimal("1500"));
        request.setCurrency("MXN");
        request.setDescription("ESTA ES UNA PRUEBA DE SPRINGBOOTTEST");
        request.setType(TransactionType.CREDIT);

        //EJECUTA TODO EL FLUJO: Controller → Service → Repository → DB
        String responseJson = mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Idempotency-Key", UUID.randomUUID())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").value("acc-123"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //CONVIERTE RESPUESTA
        TransactionResponse response =
                objectMapper.readValue(responseJson, TransactionResponse.class);

        //VERIFICACIÓN EN BD REAL
        Transaction saved = transactionRepository.findById(response.getId())
                .orElseThrow();

        assertEquals("acc-123", saved.getAccountId());
        assertEquals(new BigDecimal("5500.00"), saved.getBalanceAfter());
        assertEquals(TransactionStatus.APPROVED, saved.getStatus());
    }

    @Test
    void createTransaction_TransactionStatusRejected() throws Exception {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId("acc-123");
        request.setAmount(new BigDecimal("47770"));
        request.setCurrency("MXN");
        request.setDescription("ESTA ES UNA PRUEBA DE SPRINGBOOTTEST");
        request.setType(TransactionType.CREDIT);

        //EJECUTA TODO EL FLUJO: Controller → Service → Repository → DB
        String responseJson = mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Idempotency-Key", UUID.randomUUID())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").value("acc-123"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //CONVIERTE RESPUESTA
        TransactionResponse response =
                objectMapper.readValue(responseJson, TransactionResponse.class);

        //VERIFICACIÓN EN BD REAL
        Transaction saved = transactionRepository.findById(response.getId())
                .orElseThrow();

        assertEquals("acc-123", saved.getAccountId());
        assertEquals(null, saved.getBalanceAfter());
        assertEquals(TransactionStatus.REJECTED, saved.getStatus());
    }

    @Test
    void createTransaction_UnsupportedCurrency() throws Exception {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId("acc-123");
        request.setAmount(new BigDecimal("47770"));
        request.setCurrency("USD");
        request.setDescription("ESTA ES UNA PRUEBA DE SPRINGBOOTTEST");
        request.setType(TransactionType.CREDIT);

        //EJECUTA TODO EL FLUJO: Controller → Service → Repository → DB
        String responseJson = mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Idempotency-Key", UUID.randomUUID())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("UNSUPPORTED_CURRENCY"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //CONVIERTE RESPUESTA
        ErrorResponse errorResponse =
                objectMapper.readValue(responseJson, ErrorResponse.class);

        assertEquals("UNSUPPORTED_CURRENCY", errorResponse.getError());
        assertEquals("Only MXN currency is supported", errorResponse.getMessage());
        assertEquals(400, errorResponse.getStatus());
    }

    @Test
    void createTransaction_DebitLimitExceeded() throws Exception {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId("acc-123");
        request.setAmount(new BigDecimal("10001"));
        request.setCurrency("MXN");
        request.setDescription("ESTA ES UNA PRUEBA DE SPRINGBOOTTEST");
        request.setType(TransactionType.DEBIT);

        //EJECUTA TODO EL FLUJO: Controller → Service → Repository → DB
        String responseJson = mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Idempotency-Key", UUID.randomUUID())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("DEBIT_LIMIT_EXCEEDED"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //CONVIERTE RESPUESTA
        ErrorResponse errorResponse =
                objectMapper.readValue(responseJson, ErrorResponse.class);

        assertEquals("DEBIT_LIMIT_EXCEEDED", errorResponse.getError());
        assertEquals("DEBIT cannot exceed $10,000", errorResponse.getMessage());
        assertEquals(400, errorResponse.getStatus());
    }

    @Test
    void createTransaction_InvalidAmount() throws Exception {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId("acc-123");
        request.setAmount(new BigDecimal("1.00"));
        request.setCurrency("MXN");
        request.setDescription("ESTA ES UNA PRUEBA DE SPRINGBOOTTEST");
        request.setType(TransactionType.DEBIT);

        //EJECUTA TODO EL FLUJO: Controller → Service → Repository → DB
        String responseJson = mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Idempotency-Key", UUID.randomUUID())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("INVALID_AMOUNT"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //CONVIERTE RESPUESTA
        ErrorResponse errorResponse =
                objectMapper.readValue(responseJson, ErrorResponse.class);

        assertEquals("INVALID_AMOUNT", errorResponse.getError());
        assertEquals("Amount must be greater than $1.00", errorResponse.getMessage());
        assertEquals(400, errorResponse.getStatus());
    }

    @Test
    void getTransactions_shouldReturnPaginatedResults() throws Exception {

        //EJECUTA FLUJO COMPLETO (Controller → Service → Spec → DB)
        mockMvc.perform(get("/api/v1/transactions/")
                        .param("status", "APPROVED")
                        .param("page", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(10))
                .andExpect(jsonPath("$.meta.page").value(0))
                .andExpect(jsonPath("$.meta.limit").value(10));
    }
}
