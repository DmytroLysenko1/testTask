package org.example.service;

import org.example.dto.BankAccountDTO;
import org.example.dto.DepositDTO;
import org.example.dto.TransferDTO;
import org.example.dto.WithdrawalDTO;
import org.example.entity.BankAccount;
import org.example.entity.User;
import org.example.exceptions.exception.InsufficientFundsException;
import org.example.exceptions.exception.NotFoundException;
import org.example.repository.BankAccountRepository;
import org.example.repository.UserRepository;
import org.example.service.serviceImpl.BankAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplTest {
    @Mock
    private BankAccountRepository bankAccountRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private BankAccount bankAccount;
    private BankAccountDTO bankAccountDTO;
    private User user;

    @BeforeEach
    void setUp() {
        this.bankAccount = new BankAccount();
        this.bankAccount.setId(1L);
        this.bankAccount.setBalance(BigDecimal.ZERO);

        this.bankAccountDTO = new BankAccountDTO();
        this.bankAccountDTO.setId(1L);
        this.bankAccountDTO.setUserId(1L);

        this.user = new User();
        this.user.setId(1L);
    }

    @Test
    void testGetAllBankAccounts() {
        when(this.bankAccountRepo.findAll()).thenReturn(List.of(this.bankAccount));
        when(this.modelMapper.map(any(BankAccount.class), eq(BankAccountDTO.class)))
                .thenReturn(this.bankAccountDTO);

        List<BankAccountDTO> result = this.bankAccountService.getAllBankAccounts();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(this.bankAccountRepo, times(1)).findAll();
    }

    @Test
    void testGetBankAccountById() {
        when(this.bankAccountRepo.findById(anyLong())).thenReturn(Optional.of(this.bankAccount));
        when(this.modelMapper.map(any(BankAccount.class), eq(BankAccountDTO.class)))
                .thenReturn(this.bankAccountDTO);

        BankAccountDTO result = this.bankAccountService.getBankAccountById(1L);

        assertNotNull(result);
        assertEquals(this.bankAccountDTO.getId(), result.getId());
        verify(this.bankAccountRepo, times(1)).findById(anyLong());
    }

    @Test
    void testCreateBankAccount() {
        when(this.userRepo.findById(anyLong())).thenReturn(Optional.of(this.user));
        when(this.modelMapper.map(eq(this.bankAccountDTO), eq(BankAccount.class))).thenReturn(this.bankAccount);
        when(this.bankAccountRepo.save(any(BankAccount.class))).thenReturn(this.bankAccount);
        when(this.modelMapper.map(eq(this.bankAccount), eq(BankAccountDTO.class))).thenReturn(this.bankAccountDTO);

        BankAccountDTO result = this.bankAccountService.createBankAccount(this.bankAccountDTO);

        assertNotNull(result);
        assertEquals(this.bankAccountDTO.getId(), result.getId());
        verify(this.bankAccountRepo, times(1)).save(any(BankAccount.class));
    }

    @Test
    void testDeleteBankAccount() {
        when(this.bankAccountRepo.existsById(anyLong())).thenReturn(true);

        this.bankAccountService.deleteBankAccount(1L);

        verify(this.bankAccountRepo, times(1)).deleteById(anyLong());
    }

    @Test
    void testDepositFunds() {
        when(this.bankAccountRepo.findById(anyLong())).thenReturn(Optional.of(this.bankAccount));
        when(this.bankAccountRepo.save(any(BankAccount.class))).thenReturn(this.bankAccount);

        DepositDTO depositDTO = new DepositDTO();
        depositDTO.setAccountId(1L);
        depositDTO.setAmount(BigDecimal.TEN);

        this.bankAccountService.depositFunds(depositDTO);

        assertEquals(BigDecimal.TEN, this.bankAccount.getBalance());
        verify(this.bankAccountRepo, times(1)).save(any(BankAccount.class));
    }

    @Test
    void testWithdrawFunds() {
        this.bankAccount.setBalance(BigDecimal.TEN);
        when(this.bankAccountRepo.findById(anyLong())).thenReturn(Optional.of(this.bankAccount));
        when(this.bankAccountRepo.save(any(BankAccount.class))).thenReturn(this.bankAccount);

        WithdrawalDTO withdrawalDTO = new WithdrawalDTO();
        withdrawalDTO.setAccountId(1L);
        withdrawalDTO.setAmount(BigDecimal.ONE);

        this.bankAccountService.withdrawFunds(withdrawalDTO);

        assertEquals(BigDecimal.valueOf(9), this.bankAccount.getBalance());
        verify(this.bankAccountRepo, times(1)).save(any(BankAccount.class));
    }

    @Test
    void testTransferFunds() {
        BankAccount destinationAccount = new BankAccount();
        destinationAccount.setId(2L);
        destinationAccount.setBalance(BigDecimal.ZERO);

        this.bankAccount.setBalance(BigDecimal.TEN);

        when(this.bankAccountRepo.findById(1L)).thenReturn(Optional.of(this.bankAccount));
        when(this.bankAccountRepo.findById(2L)).thenReturn(Optional.of(destinationAccount));
        when(this.bankAccountRepo.save(any(BankAccount.class))).thenReturn(this.bankAccount);

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSourceAccountId(1L);
        transferDTO.setDestinationAccountId(2L);
        transferDTO.setAmount(BigDecimal.ONE);

        this.bankAccountService.transferFunds(transferDTO);

        assertEquals(BigDecimal.valueOf(9), this.bankAccount.getBalance());
        assertEquals(BigDecimal.ONE, destinationAccount.getBalance());
        verify(this.bankAccountRepo, times(2)).save(any(BankAccount.class));
    }

    @Test
    void testWithdrawFunds_InsufficientFunds() {
        this.bankAccount.setBalance(BigDecimal.ONE);
        when(this.bankAccountRepo.findById(anyLong())).thenReturn(Optional.of(this.bankAccount));

        WithdrawalDTO withdrawalDTO = new WithdrawalDTO();
        withdrawalDTO.setAccountId(1L);
        withdrawalDTO.setAmount(BigDecimal.TEN);

        assertThrows(InsufficientFundsException.class, () -> this.bankAccountService.withdrawFunds(withdrawalDTO));
    }

    @Test
    void testGetBankAccountById_NotFound() {
        when(this.bankAccountRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.bankAccountService.getBankAccountById(1L));
    }

    @Test
    void testDeleteBankAccount_NotFound() {
        when(this.bankAccountRepo.existsById(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> this.bankAccountService.deleteBankAccount(1L));
    }
}
