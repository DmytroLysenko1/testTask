package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailAccountDTO {
    private Long id;
    private LocalDate reportingDate;
    private BigDecimal sum;
    private BigDecimal percentage;
    private BigDecimal discountRate;
    private Long totalSum;
    private Long bankAccountId;
}
