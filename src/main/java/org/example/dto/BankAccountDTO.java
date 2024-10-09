package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
