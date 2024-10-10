package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.BankAccountDTO;
import org.example.dto.TransferDTO;
import org.example.dto.WithdrawalDTO;
import org.example.exceptions.exception.InsufficientFundsException;
import org.example.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Collections;

@WebMvcTest(BankAccountController.class)
public class BankAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BankAccountService bankAccountService;

    private static final String BASE_URL = "/bank-accounts";
    private final BankAccountDTO bankAccountDTO = new BankAccountDTO();

    @BeforeEach
    public void setUp() {
        this.bankAccountDTO.setId(1L);
        this.bankAccountDTO.setBankNumber("123456");
        this.bankAccountDTO.setType("SAVINGS");
        this.bankAccountDTO.setStatus("ACTIVE");
        this.bankAccountDTO.setUserId(1L);
    }

    @Test
    public void test_getAllBankAccounts_returnsEmptyList() throws Exception {
        when(this.bankAccountService.getAllBankAccounts())
                .thenReturn(Collections.emptyList());

        this.mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void test_getBankAccountById_returnsBankAccount() throws Exception {

        when(this.bankAccountService.getBankAccountById(anyLong()))
                .thenReturn(bankAccountDTO);

        this.mockMvc.perform(get(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": 1
                        }
                        """));
    }

    @Test
    public void test_getBankAccountById_returnsNotFound() throws Exception {
        when(this.bankAccountService.getBankAccountById(anyLong()))
                .thenThrow(new EntityNotFoundException("Bank account not found"));

        this.mockMvc.perform(get(BASE_URL + "/999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_createBankAccount_returnsCreatedBankAccount() throws Exception {
        when(this.bankAccountService.createBankAccount(any(BankAccountDTO.class)))
                .thenReturn(bankAccountDTO);

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
                "bankNumber": "123456",
                "type": "SAVINGS",
                "status": "ACTIVE",
                "userId": 1
            }
            """))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                        {
                            "id": 1
                        }
                        """));
    }

    @Test
    public void test_createBankAccount_invalidFieldValues() throws Exception {
        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                              "bankNumber": "",
                              "type": "",
                              "status": "",
                              "userId": null
                         }
            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_createBankAccount_missingRequiredFields() throws Exception {
        String invalidAccountJson = "{}";

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidAccountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_updateBankAccount_returnsUpdatedBankAccount() throws Exception {
        when(this.bankAccountService.updateBankAccount(anyLong(), any(BankAccountDTO.class)))
                .thenReturn(bankAccountDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "userId": 1
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": 1
                        }
                        """));
    }

    @Test
    public void test_deleteBankAccount_returnsNoContent() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_deleteNonExistentBankAccount_returnsNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Bank account not found"))
                .when(bankAccountService).deleteBankAccount(anyLong());

        this.mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_depositFunds_returnsOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                  {
                    "accountId": 1,
                    "amount": 100
                  }
                """))
                .andExpect(status().isOk());
    }

    @Test
    public void test_withdrawFunds_returnsOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                  {
                    "accountId": 1,
                    "amount": 100
                  }
                """))
                .andExpect(status().isOk());
    }

    @Test
    public void test_withdrawFunds_insufficientBalance_returnsBadRequest() throws Exception {
        WithdrawalDTO withdrawalDTO = new WithdrawalDTO();
        withdrawalDTO.setAccountId(1L);
        withdrawalDTO.setAmount(new BigDecimal("1000"));

        doThrow(new InsufficientFundsException("Insufficient funds"))
                .when(bankAccountService).withdrawFunds(any(WithdrawalDTO.class));

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                  {
                    "accountId": 1,
                    "amount": 100
                  }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_transferFunds_returnsOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                  {
                    "sourceAccountId": 1,
                    "destinationAccountId": 2,
                    "amount": 100
                  }
                  """))
                .andExpect(status().isOk());
    }

    @Test
    public void test_transferFunds_insufficientBalance_returnsBadRequest() throws Exception {
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSourceAccountId(1L);
        transferDTO.setDestinationAccountId(2L);
        transferDTO.setAmount(new BigDecimal("1000"));

        doThrow(new InsufficientFundsException("Insufficient funds"))
                .when(bankAccountService).transferFunds(any(TransferDTO.class));

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                  {
                    "sourceAccountId": 1,
                    "destinationAccountId": 2,
                    "amount": 1000
                  }
                  """))
                .andExpect(status().isBadRequest());
    }
}
