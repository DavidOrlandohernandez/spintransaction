package dto;

import com.spin.transaction.numbregeneratorservice.TransactionType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class TransactionRequest {

    @NotBlank
    @Size(max = 50)
    private String accountId;

    @NotNull
    private TransactionType type;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be 3 uppercase letters (ISO 4217)")
    private String currency;

    @Size(max = 255)
    private String description;
}