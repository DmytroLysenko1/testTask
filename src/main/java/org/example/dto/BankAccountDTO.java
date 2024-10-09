package org.example.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
public class BankAccountDTO {
    private Long id;
    private String bankNumber;
    private String type;
    private String status;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String issuedCard;
    private LocalDate validUntil;
    private Long userId;
    private List<DetailAccountDTO> detailAccounts;
}
