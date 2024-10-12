package org.example.utils;

import org.example.dto.*;
import org.example.entity.BankAccount;
import org.example.entity.DetailAccount;
import org.example.entity.User;
import org.example.enums.Role;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ModelUtils {
    public static BankAccountDTO getBankAccountDTO() {
        return BankAccountDTO.builder()
                .id(1L)
                .bankNumber("123456")
                .type("SAVINGS")
                .status("ACTIVE")
                .userId(1L)
                .build();
    }

    public static BankAccountDTO getBankAccountDTOSimplified() {
        return BankAccountDTO.builder()
                .id(1L)
                .userId(1L)
                .build();
    }

    public static BankAccount getBankAccount() {
        return BankAccount.builder()
                .id(1L)
                .balance(BigDecimal.ZERO)
                .build();
    }

    public static BankAccount getBankAccount2() {
        return BankAccount.builder()
                .id(2L)
                .balance(BigDecimal.ZERO)
                .build();
    }

    public static User getUser() {
        return User.builder()
                .id(1L)
                .username("John Doe")
                .email("john.doe@example.com")
                .role(Role.USER)
                .password("password")
                .build();
    }

    public static User getUserSimplified() {
        return User.builder()
                .id(1L)
                .build();
    }

    public static UserDTO getUserDTO() {
        return UserDTO.builder()
                .username("John Doe")
                .email("john.doe@example.com")
                .password("password")
                .build();
    }

    public static UserDTO getUserDTO2() {
        return UserDTO.builder()
                .username("Jane Doe")
                .email("jane.doe@example.com")
                .password("password")
                .build();
    }

    public static DetailAccountDTO getDetailAccountDTO() {
        return DetailAccountDTO.builder()
                .id(1L)
                .reportingDate(LocalDate.now())
                .sum(new BigDecimal("1000"))
                .percentage(new BigDecimal("5"))
                .discountRate(new BigDecimal("2"))
                .totalSum(1000L)
                .bankAccountId(1L)
                .build();
    }

    public static DetailAccountDTO getDetailAccountDTOSimplified() {
        return DetailAccountDTO.builder()
                .id(1L)
                .build();
    }
    public static DetailAccount getDetailAccountSimplified() {
        return DetailAccount.builder()
                .id(1L)
                .build();
    }

    public static TransferDTO getTransferDTO() {
        return TransferDTO.builder()
                .sourceAccountId(1L)
                .destinationAccountId(2L)
                .amount(new BigDecimal("100"))
                .build();
    }

    public static WithdrawalDTO getWithdrawalDTO() {
        return WithdrawalDTO.builder()
                .accountId(1L)
                .amount(new BigDecimal("100"))
                .build();
    }

    public static WithdrawalDTO getWithdrawalDTOAnalog() {
        return WithdrawalDTO.builder()
                .accountId(1L)
                .amount(BigDecimal.TEN)
                .build();
    }

    public static DepositDTO getDepositDTO() {
        return DepositDTO.builder()
                .accountId(1L)
                .amount(BigDecimal.TEN)
                .build();
    }
}
