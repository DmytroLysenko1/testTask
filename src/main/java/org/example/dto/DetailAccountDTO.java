package org.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailAccountDTO {
    private Long id;
    @NotNull
    private LocalDate reportingDate;
    @NotNull
    @Positive
    private BigDecimal sum;
    @NotNull
    @Positive
    private BigDecimal percentage;
    @NotNull
    @Positive
    private BigDecimal discountRate;
    @NotNull
    @Positive
    private Long totalSum;
    @NotNull
    private Long bankAccountId;
}
