package org.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDTO {
    @NotNull(message = "Source Account ID cannot be null")
    @Positive(message = "Source Account ID must be a positive number")
    private Long sourceAccountId;

    @NotNull(message = "Destination Account ID cannot be null")
    @Positive(message = "Destination Account ID must be a positive number")
    private Long destinationAccountId;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be a positive number")
    private BigDecimal amount;
}
