package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDTO {
    private Long id;
    @NotBlank
    private String bankNumber;
    @NotNull
    private String type;
    @NotBlank
    private String status;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String issuedCard;
    private LocalDate validUntil;
    private BigDecimal balance;
    @NotNull
    private Long userId;
    private List<DetailAccountDTO> detailAccounts;
}
