package org.example.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DetailAccountDTO {
    private Long id;
    private LocalDate reportingDate;
    private BigDecimal sum;
    private BigDecimal percentage;
    private BigDecimal discountRate;
    private Long totalSum;
    private Long bankAccountId;
}
