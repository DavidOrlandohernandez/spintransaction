package com.spin.transaction.controller;

import com.spin.transaction.enums.TransactionStatus;
import com.spin.transaction.enums.TransactionType;
import com.spin.transaction.service.ITransactionService;
import com.spin.transaction.dto.transaction.TransactionRequest;
import com.spin.transaction.dto.transaction.TransactionResponse;
import com.spin.transaction.wraper.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transaction - Controller",
        description = "API REST que gestiona la ejecución de transacciones financieras (crédito y débito).")
public class TransactionController {

    private static final Logger log =
            LoggerFactory.getLogger(TransactionController.class);

    private final ITransactionService transactionServices;

    public TransactionController(ITransactionService transactionServices) {
        this.transactionServices = transactionServices;
    }

    @Operation(
            summary = "ALTA DE UNA TRANSACCIÓN / CREATE",
            description =  "Método creado para  consultar y almacenar toda la información de una transacción",
            tags = {"ALTA DE TRANSACCIÓN"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Estructura del cuerpo en formato Json clase TransactionRequest / POST",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TransactionRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "201 Created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransactionResponse.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<TransactionResponse> create(
            @Valid @RequestBody TransactionRequest transactionRequest) {

        log.info("POST /Transaction recibido: {}", transactionRequest);
        TransactionResponse response = transactionServices.create(transactionRequest);
        log.info("POST /Transaction response generado: {}", response);

        return ResponseEntity
                .created(URI.create("/api/v1/transactions/" + response.getId()))
                .body(response);
    }

    @Operation(
            summary = "CONSULTA DE TRANSACCIÓN / GETTRANSACTIONS",
            description =  "Método creado para obtener transacciones paginadas y filtradas",
            tags = {"CONSULTA DE TRANSACCIÓN"},
            parameters = {
                    @Parameter(name = "accountId", description = "Identificador de la cuenta", example = "acc-123456"),
                    @Parameter(name = "status", description = "Estado de la transacción", example = "APPROVED/REJECTED"),
                    @Parameter(name = "type", description = "Tipo de tarjeta", example = "CREDIT/DEBIT"),
                    @Parameter(name = "page", description = "Indicador de numero de pagina", example = "0"),
                    @Parameter(name = "limit", description = "limite de transacciones", example = "10")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful (OK)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PageResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/")
    public  ResponseEntity<PageResponse<TransactionResponse>>  getTransactions(
            @RequestParam(required = false) String accountId,
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        log.info("GET /transactions filters accountId={}, status={}, type={}, page={}, limit={}",
                accountId, status, type, page, limit);

        return ResponseEntity.ok(
                transactionServices.findTransactions(accountId, status, type, page, limit)
        );
    }


    @GetMapping
    public ResponseEntity<?> findTransaction() {
        List<TransactionResponse> response = transactionServices.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTransactionById(@PathVariable UUID id) {
        TransactionResponse transaction = transactionServices.findTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

}
