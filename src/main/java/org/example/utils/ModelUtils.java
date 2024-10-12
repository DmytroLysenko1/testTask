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
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setId(1L);
        bankAccountDTO.setBankNumber("123456");
        bankAccountDTO.setType("SAVINGS");
        bankAccountDTO.setStatus("ACTIVE");
        bankAccountDTO.setUserId(1L);
        return bankAccountDTO;
    }

    public static BankAccountDTO getBankAccountDTOSimplified() {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setId(1L);
        bankAccountDTO.setUserId(1L);
        return bankAccountDTO;
    }

    public static BankAccount getBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setBalance(BigDecimal.ZERO);
        return bankAccount;
    }

    public static BankAccount getBankAccount2() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(2L);
        bankAccount.setBalance(BigDecimal.ZERO);
        return bankAccount;
    }

    public static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("John Doe");
        user.setEmail("john.doe@example.com");
        user.setRole(Role.USER);
        user.setPassword("password");
        return user;
    }

    public static User getUserSimplified() {
        User user = new User();
        user.setId(1L);
        return user;
    }

    public static UserDTO getUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("John Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("password");
        return userDTO;
    }

    public static UserDTO getUserDTO2() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("Jane Doe");
        userDTO.setEmail("jane.doe@example.com");
        userDTO.setPassword("password");
        return userDTO;
    }

    public static DetailAccountDTO getDetailAccountDTO() {
        DetailAccountDTO detailAccountDTO = new DetailAccountDTO();
        detailAccountDTO.setId(1L);
        detailAccountDTO.setReportingDate(LocalDate.now());
        detailAccountDTO.setSum(new BigDecimal("1000"));
        detailAccountDTO.setPercentage(new BigDecimal("5"));
        detailAccountDTO.setDiscountRate(new BigDecimal("2"));
        detailAccountDTO.setTotalSum(1000L);
        detailAccountDTO.setBankAccountId(1L);
        return detailAccountDTO;
    }

    public static DetailAccountDTO getDetailAccountDTOSimplified() {
        DetailAccountDTO detailAccount = new DetailAccountDTO();
        detailAccount.setId(1L);
        return detailAccount;
    }
    public static DetailAccount getDetailAccountSimplified() {
        DetailAccount detailAccount = new DetailAccount();
        detailAccount.setId(1L);
        return detailAccount;
    }

    public static TransferDTO getTransferDTO() {
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSourceAccountId(1L);
        transferDTO.setDestinationAccountId(2L);
        transferDTO.setAmount(new BigDecimal("100"));
        return transferDTO;
    }

    public static WithdrawalDTO getWithdrawalDTO() {
        WithdrawalDTO withdrawalDTO = new WithdrawalDTO();
        withdrawalDTO.setAccountId(1L);
        withdrawalDTO.setAmount(new BigDecimal("100"));
        return withdrawalDTO;
    }

    public static WithdrawalDTO getWithdrawalDTOAnalog() {
        WithdrawalDTO withdrawalDTO = new WithdrawalDTO();
        withdrawalDTO.setAccountId(1L);
        withdrawalDTO.setAmount(BigDecimal.ONE);
        return withdrawalDTO;
    }

    public static DepositDTO getDepositDTO() {
        DepositDTO depositDTO = new DepositDTO();
        depositDTO.setAccountId(1L);
        depositDTO.setAmount(BigDecimal.TEN);
        return depositDTO;
    }
}
