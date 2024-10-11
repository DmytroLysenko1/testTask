package org.example.service;

import org.example.dto.DetailAccountDTO;
import org.example.exceptions.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DetailAccountService {
    List<DetailAccountDTO> getAllDetailAccounts();
    DetailAccountDTO getDetailAccountById(Long id);
    DetailAccountDTO createDetailAccount(DetailAccountDTO detailAccountDTO);
    DetailAccountDTO updateDetailAccount(Long id, DetailAccountDTO detailAccountDTO);
    void deleteDetailAccount(Long id) throws NotFoundException;
}
