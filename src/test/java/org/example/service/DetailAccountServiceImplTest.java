package org.example.service;

import org.example.dto.DetailAccountDTO;
import org.example.entity.DetailAccount;
import org.example.exceptions.exception.NotFoundException;
import org.example.repository.DetailAccountRepository;
import org.example.service.serviceImpl.DetailAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetailAccountServiceImplTest {

    @Mock
    private DetailAccountRepository detailAccountRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DetailAccountServiceImpl detailAccountService;

    private DetailAccount detailAccount;
    private DetailAccountDTO detailAccountDTO;

    @BeforeEach
    void setUp() {
        this.detailAccount = new DetailAccount();
        this.detailAccount.setId(1L);

        this.detailAccountDTO = new DetailAccountDTO();
        this.detailAccountDTO.setId(1L);
    }

    @Test
    void testGetAllDetailAccounts() {
        when(this.detailAccountRepo.findAll()).thenReturn(List.of(this.detailAccount));
        when(this.modelMapper.map(any(DetailAccount.class), eq(DetailAccountDTO.class)))
                .thenReturn(this.detailAccountDTO);

        List<DetailAccountDTO> result = this.detailAccountService.getAllDetailAccounts();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(this.detailAccountRepo, times(1)).findAll();
    }

    @Test
    void testGetDetailAccountById() {
        when(this.detailAccountRepo.findById(anyLong())).thenReturn(Optional.of(this.detailAccount));
        when(this.modelMapper.map(any(DetailAccount.class), eq(DetailAccountDTO.class)))
                .thenReturn(this.detailAccountDTO);

        DetailAccountDTO result = this.detailAccountService.getDetailAccountById(1L);

        assertNotNull(result);
        assertEquals(this.detailAccountDTO.getId(), result.getId());
        verify(this.detailAccountRepo, times(1)).findById(anyLong());
    }

    @Test
    void testCreateDetailAccount() {
        when(this.modelMapper.map(any(DetailAccountDTO.class), eq(DetailAccount.class)))
                .thenReturn(this.detailAccount);
        when(this.detailAccountRepo.save(any(DetailAccount.class))).thenReturn(this.detailAccount);
        when(this.modelMapper.map(any(DetailAccount.class), eq(DetailAccountDTO.class)))
                .thenReturn(this.detailAccountDTO);

        DetailAccountDTO result = this.detailAccountService.createDetailAccount(this.detailAccountDTO);

        assertNotNull(result);
        assertEquals(this.detailAccountDTO.getId(), result.getId());
        verify(this.detailAccountRepo, times(1)).save(any(DetailAccount.class));
    }

    @Test
    void testUpdateDetailAccount() {
        when(this.detailAccountRepo.findById(anyLong())).thenReturn(Optional.of(this.detailAccount));
        doAnswer(invocation -> {
            DetailAccountDTO source = invocation.getArgument(0);
            DetailAccount target = invocation.getArgument(1);
            target.setId(source.getId());
            return null;
        }).when(this.modelMapper).map(any(DetailAccountDTO.class), any(DetailAccount.class));
        when(this.detailAccountRepo.save(any(DetailAccount.class))).thenReturn(this.detailAccount);
        when(this.modelMapper.map(any(DetailAccount.class), eq(DetailAccountDTO.class)))
                .thenReturn(this.detailAccountDTO);

        DetailAccountDTO result = this.detailAccountService.updateDetailAccount(1L, this.detailAccountDTO);

        assertNotNull(result);
        assertEquals(this.detailAccountDTO.getId(), result.getId());
        verify(this.detailAccountRepo, times(1)).save(any(DetailAccount.class));
    }

    @Test
    void testDeleteDetailAccount() {
        when(this.detailAccountRepo.existsById(anyLong())).thenReturn(true);

        this.detailAccountService.deleteDetailAccount(1L);

        verify(this.detailAccountRepo, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteDetailAccount_NotFound() {
        when(this.detailAccountRepo.existsById(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> this.detailAccountService.deleteDetailAccount(1L));
    }


    @Test
    void testGetDetailAccountById_NotFound() {
        when(this.detailAccountRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.detailAccountService.getDetailAccountById(1L));
        verify(this.detailAccountRepo, times(1)).findById(anyLong());
    }

    @Test
    void testCreateDetailAccount_NullDTO() {
        assertThrows(IllegalArgumentException.class, () -> this.detailAccountService.createDetailAccount(null));
        verify(this.detailAccountRepo, never()).save(any(DetailAccount.class));
    }

    @Test
    void testUpdateDetailAccount_NotFound() {
        when(this.detailAccountRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                this.detailAccountService.updateDetailAccount(1L, this.detailAccountDTO));
        verify(this.detailAccountRepo, times(1)).findById(anyLong());
        verify(this.detailAccountRepo, never()).save(any(DetailAccount.class));
    }

    @Test
    void testUpdateDetailAccount_NullDTO() {
        assertThrows(IllegalArgumentException.class, () -> this.detailAccountService.updateDetailAccount(1L, null));
        verify(this.detailAccountRepo, never()).findById(anyLong());
        verify(this.detailAccountRepo, never()).save(any(DetailAccount.class));
    }

    // src/test/java/org/example/service/DetailAccountServiceImplTest.java
    @Test
    void testDeleteDetailAccount_Exception() {
        when(this.detailAccountRepo.existsById(anyLong())).thenReturn(true);
        doThrow(new RuntimeException("Database error")).when(this.detailAccountRepo).deleteById(anyLong());

        assertThrows(RuntimeException.class, () -> this.detailAccountService.deleteDetailAccount(1L));
        verify(this.detailAccountRepo, times(1)).existsById(anyLong());
        verify(this.detailAccountRepo, times(1)).deleteById(anyLong());
    }
}
