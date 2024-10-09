package org.example.service;

import org.example.dto.BankAccountDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankAccountService {
    List<BankAccountDTO> getAllBankAccounts();
    BankAccountDTO getBankAccountById(Long id);
    BankAccountDTO createBankAccount(BankAccountDTO bankAccountDTO);
    BankAccountDTO updateBankAccount(Long id, BankAccountDTO bankAccountDTO);
    void deleteBankAccount(Long id);
}
