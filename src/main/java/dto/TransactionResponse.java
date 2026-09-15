package dto;

import com.spin.transaction.numbregeneratorservice.TransactionType;
import com.spin.transaction.numbregeneratorservice.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private UUID id;
    private String accountId;
    private TransactionType type;
    private BigDecimal amount;
    private String currency;
    private String description;
    private TransactionStatus status;
    private String providerTransactionId;
    private BigDecimal balanceAfter;
    private OffsetDateTime createdAt;


}