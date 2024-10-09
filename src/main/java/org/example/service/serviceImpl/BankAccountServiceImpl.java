package org.example.service.serviceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.constant.ErrorMessage;
import org.example.dto.BankAccountDTO;
import org.example.dto.DepositDTO;
import org.example.dto.TransferDTO;
import org.example.dto.WithdrawalDTO;
import org.example.entity.BankAccount;
import org.example.exceptions.exception.NotFoundException;
import org.example.repository.BankAccountRepository;
import org.example.service.BankAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<BankAccountDTO> getAllBankAccounts() {
        return this.bankAccountRepository.findAll().stream().map(
                bankAccount -> this.modelMapper.map(bankAccount, BankAccountDTO.class))
                .toList();
    }

    @Override
    public BankAccountDTO getBankAccountById(Long id) {
        BankAccount bankAccount = this.bankAccountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BANKACCOUNT_NOT_FOUND_BY_ID + id));
        return this.modelMapper.map(bankAccount, BankAccountDTO.class);
    }

    @Override
    @Transactional
    public BankAccountDTO createBankAccount(@Valid BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = this.modelMapper.map(bankAccountDTO, BankAccount.class);
        BankAccount savedBankAccount = this.bankAccountRepository.save(bankAccount);
        return this.modelMapper.map(savedBankAccount, BankAccountDTO.class);
    }

    @Override
    @Transactional
    public BankAccountDTO updateBankAccount(Long id, @Valid BankAccountDTO bankAccountDTO) {
        BankAccount existingBankAccount = this.bankAccountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BANKACCOUNT_NOT_FOUND_BY_ID + id));
        this.modelMapper.map(bankAccountDTO, existingBankAccount);
        BankAccount updatedBankAccount = this.bankAccountRepository.save(existingBankAccount);
        return this.modelMapper.map(updatedBankAccount, BankAccountDTO.class);
    }

    @Override
    @Transactional
    public void deleteBankAccount(Long id) {
        if (!this.bankAccountRepository.existsById(id)) {
            throw new NotFoundException(ErrorMessage.BANKACCOUNT_NOT_FOUND_BY_ID + id);
        }
        this.bankAccountRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void depositFunds(DepositDTO depositDTO) {
        BankAccount bankAccount = this.bankAccountRepository.findById(depositDTO.getAccountId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BANKACCOUNT_NOT_FOUND_BY_ID + depositDTO.getAccountId()));
        bankAccount.setBalance(bankAccount.getBalance().add(depositDTO.getAmount()));
        this.bankAccountRepository.save(bankAccount);
    }

    @Override
    public void withdrawFunds(WithdrawalDTO withdrawalDTO) {
        BankAccount bankAccount = this.bankAccountRepository.findById(withdrawalDTO.getAccountId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BANKACCOUNT_NOT_FOUND_BY_ID + withdrawalDTO.getAccountId()));

        if (bankAccount.getBalance().compareTo(withdrawalDTO.getAmount()) < 0) {
            throw new IllegalArgumentException(ErrorMessage.INSUFFICIENT_FUNDS);
        }
        bankAccount.setBalance(bankAccount.getBalance().subtract(withdrawalDTO.getAmount()));
        this.bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transferFunds(TransferDTO transferDTO) {
        BankAccount sourceAccount = this.bankAccountRepository.findById(transferDTO.getSourceAccountId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BANKACCOUNT_NOT_FOUND_BY_ID + transferDTO.getSourceAccountId()));
        BankAccount destinationAccount = this.bankAccountRepository.findById(transferDTO.getDestinationAccountId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BANKACCOUNT_NOT_FOUND_BY_ID + transferDTO.getDestinationAccountId()));

        if (sourceAccount.getBalance().compareTo(transferDTO.getAmount()) < 0) {
            throw new IllegalArgumentException(ErrorMessage.INSUFFICIENT_FUNDS);
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferDTO.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(transferDTO.getAmount()));
        this.bankAccountRepository.save(sourceAccount);
        this.bankAccountRepository.save(destinationAccount);
    }
}
