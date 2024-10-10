package org.example.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class BankAccountDTO {
    private String bankNumber;
    private String type;
    private String status;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String issuedCard;
    private LocalDate validUntil;
    private BigDecimal balance;
    private Long userId;
    private List<DetailAccountDTO> detailAccounts;
}
