package org.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.constant.ServiceValidationConstants;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalDTO {
    @NotNull(message = ServiceValidationConstants.ACCOUNT_ID_NOT_NULL)
    @Positive(message = ServiceValidationConstants.ACCOUNT_ID_POSITIVE)
    private Long accountId;

    @NotNull(message = ServiceValidationConstants.AMOUNT_NOT_NULL)
    @Positive(message = ServiceValidationConstants.AMOUNT_POSITIVE)
    private BigDecimal amount;
}
