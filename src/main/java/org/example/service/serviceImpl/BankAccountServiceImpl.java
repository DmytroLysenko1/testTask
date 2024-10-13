package org.example.service.serviceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.ErrorMessage;
import org.example.constant.LogMessage;
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
        log.info(LogMessage.FETCHING_ALL_BANK_ACCOUNTS);

        return this.bankAccountRepo.findAll().stream()
                .map(bankAccount -> this.modelMapper.map(bankAccount, BankAccountDTO.class))
                .toList();
    }

    @Override
    public BankAccountDTO getBankAccountById(Long id) {
        log.info(LogMessage.FETCHING_BANK_ACCOUNT_BY_ID, id);

        BankAccount bankAccount = findBankAccountById(id);

        return this.modelMapper.map(bankAccount, BankAccountDTO.class);
    }

    @Override
    @Transactional
    public BankAccountDTO createBankAccount(@Valid BankAccountDTO bankAccountDTO) {
        log.info(LogMessage.CREATING_NEW_BANK_ACCOUNT, bankAccountDTO);

        BankAccount bankAccount = this.modelMapper.map(bankAccountDTO, BankAccount.class);

        User user = findUserById(bankAccountDTO.getUserId());
        bankAccount.setUser(user);

        initializeBalance(bankAccount);
        setDetailAccounts(bankAccount, bankAccountDTO.getDetailAccounts());

        bankAccount = this.bankAccountRepo.save(bankAccount);

        log.info(LogMessage.CREATED_BANK_ACCOUNT, bankAccount.getId());

        return this.modelMapper.map(bankAccount, BankAccountDTO.class);
    }

    @Override
    @Transactional
    public BankAccountDTO updateBankAccount(Long id, BankAccountDTO bankAccountDTO) {
        BankAccount existingBankAccount = findBankAccountById(id);

        this.modelMapper.map(bankAccountDTO, existingBankAccount);

        BankAccount updatedBankAccount = this.bankAccountRepo.save(existingBankAccount);

        log.info(LogMessage.UPDATING_BANK_ACCOUNT, id);

        return this.modelMapper.map(updatedBankAccount, BankAccountDTO.class);
    }

    @Override
    @Transactional
    public void deleteBankAccount(Long id) {
        log.info(LogMessage.DELETING_BANK_ACCOUNT, id);

        if (!this.bankAccountRepo.existsById(id)) {
            log.error(LogMessage.BANK_ACCOUNT_NOT_FOUND, id);
            throw new NotFoundException(ErrorMessage.BANKACCOUNT_NOT_FOUND_BY_ID + id);
        }
        this.bankAccountRepo.deleteById(id);

        log.info(LogMessage.DELETED_BANK_ACCOUNT, id);
    }

    @Override
    @Transactional
    public void depositFunds(DepositDTO depositDTO) {
        log.info(LogMessage.DEPOSITING_FUNDS, depositDTO.getAccountId());

        BankAccount bankAccount = findBankAccountById(depositDTO.getAccountId());

        bankAccount.setBalance(bankAccount.getBalance().add(depositDTO.getAmount()));

        this.bankAccountRepo.save(bankAccount);

        log.info(LogMessage.DEPOSITED_FUNDS, depositDTO.getAmount(), depositDTO.getAccountId());
    }

    @Override
    public void withdrawFunds(WithdrawalDTO withdrawalDTO) {
        log.info(LogMessage.WITHDRAWING_FUNDS, withdrawalDTO.getAccountId());

        BankAccount bankAccount = findBankAccountById(withdrawalDTO.getAccountId());

        validateSufficientFunds(bankAccount, withdrawalDTO.getAmount());
        bankAccount.setBalance(bankAccount.getBalance().subtract(withdrawalDTO.getAmount()));

        this.bankAccountRepo.save(bankAccount);

        log.info(LogMessage.WITHDRAWN_FUNDS, withdrawalDTO.getAmount(), withdrawalDTO.getAccountId());
    }

    @Override
    public void transferFunds(TransferDTO transferDTO) {
        log.info(LogMessage.TRANSFERRING_FUNDS, transferDTO.getSourceAccountId(),
                transferDTO.getDestinationAccountId());

        BankAccount sourceAccount = findBankAccountById(transferDTO.getSourceAccountId());
        BankAccount destinationAccount = findBankAccountById(transferDTO.getDestinationAccountId());

        validateSufficientFunds(sourceAccount, transferDTO.getAmount());
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferDTO.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(transferDTO.getAmount()));

        this.bankAccountRepo.save(sourceAccount);
        this.bankAccountRepo.save(destinationAccount);

        log.info(LogMessage.TRANSFERRED_FUNDS, transferDTO.getAmount(),
                transferDTO.getSourceAccountId(), transferDTO.getDestinationAccountId());
    }

    private BankAccount findBankAccountById(Long id) {
        log.debug(LogMessage.FINDING_BANK_ACCOUNT_BY_ID);
        return this.bankAccountRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BANKACCOUNT_NOT_FOUND_BY_ID + id));
    }

    private User findUserById(Long userId) {
        log.debug(LogMessage.FINDING_USER_BY_ID);
        return this.userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + userId));
    }

    private void initializeBalance(BankAccount bankAccount) {
        if (bankAccount.getBalance() == null) {
            log.debug(LogMessage.INITIALIZING_BALANCE, bankAccount.getId());
            bankAccount.setBalance(BigDecimal.ZERO);
        }
    }

    private void setDetailAccounts(BankAccount bankAccount, List<DetailAccountDTO> detailAccountDTOs) {
        log.debug(LogMessage.SETTING_DETAIL_ACCOUNTS, bankAccount.getId());

        if (detailAccountDTOs != null) {
            List<DetailAccount> detailAccounts = detailAccountDTOs.stream()
                    .map(detailAccountDTO -> {
                        DetailAccount detailAccount = this.modelMapper.map(detailAccountDTO, DetailAccount.class);
                        detailAccount.setBankAccount(bankAccount);
                        return detailAccount;
                    }).toList();
            bankAccount.setDetailAccounts(detailAccounts);
        }else {
            bankAccount.setDetailAccounts(null);
        }
    }

    private void validateSufficientFunds(BankAccount bankAccount, BigDecimal amount) {
        log.debug(LogMessage.VALIDATING_SUFFICIENT_FUNDS, bankAccount.getId());
        if (bankAccount.getBalance().compareTo(amount) < 0) {
            log.error(LogMessage.INSUFFICIENT_FUNDS, bankAccount.getId());
            throw new InsufficientFundsException(ErrorMessage.INSUFFICIENT_FUNDS);
        }
    }
}
