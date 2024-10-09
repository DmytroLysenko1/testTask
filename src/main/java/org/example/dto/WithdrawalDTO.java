package org.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawalDTO {
    @NotNull(message = "Account ID cannot be null")
    @Positive(message = "Account ID must be a positive number")
    private Long accountId;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be a positive number")
    private BigDecimal amount;
}
