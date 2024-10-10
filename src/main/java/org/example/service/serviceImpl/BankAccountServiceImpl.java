package org.example.service.serviceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.ErrorMessage;
import org.example.dto.*;
import org.example.entity.BankAccount;
import org.example.entity.DetailAccount;
import org.example.entity.User;
import org.example.exceptions.exception.InsufficientFundsException;
import org.example.exceptions.exception.NotFoundException;
import org.example.repository.BankAccountRepository;
import org.example.repository.UserRepository;
import org.example.service.BankAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepo;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<BankAccountDTO> getAllBankAccounts() {
        log.info("Fetching all bank accounts");
        return this.bankAccountRepo.findAll().stream()
                .map(bankAccount -> this.modelMapper.map(bankAccount, BankAccountDTO.class))
                .toList();
    }

    @Override
    public BankAccountDTO getBankAccountById(Long id) {
        log.info("Fetching bank account with id: {}", id);
        BankAccount bankAccount = findBankAccountById(id);
        return this.modelMapper.map(bankAccount, BankAccountDTO.class);
    }

    @Override
    @Transactional
    public BankAccountDTO createBankAccount(@Valid BankAccountDTO bankAccountDTO) {
        log.info("Creating a new bank account: {}", bankAccountDTO);
        BankAccount bankAccount = this.modelMapper.map(bankAccountDTO, BankAccount.class);
        User user = findUserById(bankAccountDTO.getUserId());
        bankAccount.setUser(user);
        initializeBalance(bankAccount);
        setDetailAccounts(bankAccount, bankAccountDTO.getDetailAccounts());
        bankAccount = this.bankAccountRepo.save(bankAccount);
        log.info("Created bank account with id: {}", bankAccount.getId());
        return this.modelMapper.map(bankAccount, BankAccountDTO.class);
    }

    @Override
    @Transactional
    public BankAccountDTO updateBankAccount(Long id, @Valid BankAccountDTO bankAccountDTO) {
        log.info("Updating bank account with id: {}", id);
        BankAccount existingBankAccount = findBankAccountById(id);
        this.modelMapper.map(bankAccountDTO, existingBankAccount);
        BankAccount updatedBankAccount = this.bankAccountRepo.save(existingBankAccount);
        log.info("Updated bank account with id: {}", id);
        return this.modelMapper.map(updatedBankAccount, BankAccountDTO.class);
    }

    @Override
    @Transactional
    public void deleteBankAccount(Long id) {
        log.info("Deleting bank account with id: {}", id);
        if (!this.bankAccountRepo.existsById(id)) {
            log.error("Bank account not found with id: {}", id);
            throw new NotFoundException(ErrorMessage.BANKACCOUNT_NOT_FOUND_BY_ID + id);
        }
        this.bankAccountRepo.deleteById(id);
        log.info("Deleted bank account with id: {}", id);
    }

    @Override
    @Transactional
    public void depositFunds(DepositDTO depositDTO) {
        log.info("Depositing funds to bank account with id: {}", depositDTO.getAccountId());
        BankAccount bankAccount = findBankAccountById(depositDTO.getAccountId());
        bankAccount.setBalance(bankAccount.getBalance().add(depositDTO.getAmount()));
        this.bankAccountRepo.save(bankAccount);
        log.info("Deposited {} to bank account with id: {}", depositDTO.getAmount(), depositDTO.getAccountId());
    }

    @Override
    public void withdrawFunds(WithdrawalDTO withdrawalDTO) {
        log.info("Withdrawing funds from bank account with id: {}", withdrawalDTO.getAccountId());
        BankAccount bankAccount = findBankAccountById(withdrawalDTO.getAccountId());
        validateSufficientFunds(bankAccount, withdrawalDTO.getAmount());
        bankAccount.setBalance(bankAccount.getBalance().subtract(withdrawalDTO.getAmount()));
        this.bankAccountRepo.save(bankAccount);
        log.info("Withdrew {} from bank account with id: {}", withdrawalDTO.getAmount(), withdrawalDTO.getAccountId());
    }

    @Override
    public void transferFunds(TransferDTO transferDTO) {
        log.info("Transferring funds from bank account with id: {} to bank account with id: {}",
                transferDTO.getSourceAccountId(), transferDTO.getDestinationAccountId());

        BankAccount sourceAccount = findBankAccountById(transferDTO.getSourceAccountId());
        BankAccount destinationAccount = findBankAccountById(transferDTO.getDestinationAccountId());
        validateSufficientFunds(sourceAccount, transferDTO.getAmount());
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferDTO.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(transferDTO.getAmount()));
        this.bankAccountRepo.save(sourceAccount);
        this.bankAccountRepo.save(destinationAccount);
        log.info("Transferred {} from bank account with id: {} to bank account with id: {}",
                transferDTO.getAmount(), transferDTO.getSourceAccountId(), transferDTO.getDestinationAccountId());
    }

    private BankAccount findBankAccountById(Long id) {
        log.debug("Finding bank account with id: {}", id);
        return this.bankAccountRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BANKACCOUNT_NOT_FOUND_BY_ID + id));
    }

    private User findUserById(Long userId) {
        log.debug("Finding user with id: {}", userId);
        return this.userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + userId));
    }

    private void initializeBalance(BankAccount bankAccount) {
        if (bankAccount.getBalance() == null) {
            log.debug("Initializing balance for bank account with id: {}", bankAccount.getId());
            bankAccount.setBalance(BigDecimal.ZERO);
        }
    }

    private void setDetailAccounts(BankAccount bankAccount, List<DetailAccountDTO> detailAccountDTOs) {
        log.debug("Setting detail accounts for bank account with id: {}", bankAccount.getId());
        List<DetailAccount> detailAccounts = detailAccountDTOs.stream()
                .map(detailAccountDTO -> {
                    DetailAccount detailAccount = this.modelMapper.map(detailAccountDTO, DetailAccount.class);
                    detailAccount.setBankAccount(bankAccount);
                    return detailAccount;
                }).toList();
        bankAccount.setDetailAccounts(detailAccounts);
    }

    private void validateSufficientFunds(BankAccount bankAccount, BigDecimal amount) {
        log.debug("Validating sufficient funds for bank account with id: {}", bankAccount.getId());
        if (bankAccount.getBalance().compareTo(amount) < 0) {
            log.error("Insufficient funds for bank account with id: {}", bankAccount.getId());
            throw new InsufficientFundsException(ErrorMessage.INSUFFICIENT_FUNDS);
        }
    }
}
