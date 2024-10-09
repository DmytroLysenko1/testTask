package org.example.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime birthday;
    private LocalDate dateOfRegistration;
    private Integer openAccountsCount;
    private Integer closedAccountsCount;
    private List<BankAccountDTO> bankAccounts;
}
