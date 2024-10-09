package org.example.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.constant.ErrorMessage;
import org.example.dto.DetailAccountDTO;
import org.example.entity.DetailAccount;
import org.example.exceptions.exception.NotFoundException;
import org.example.repository.DetailAccountRepository;
import org.example.service.DetailAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetailAccountServiceImpl implements DetailAccountService {

    private final DetailAccountRepository detailAccountRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<DetailAccountDTO> getAllDetailAccounts() {
        return this.detailAccountRepo.findAll().stream()
                .map(detailAccount -> this.modelMapper.map(detailAccount, DetailAccountDTO.class))
                .toList();
    }

    @Override
    public DetailAccountDTO getDetailAccountById(Long id) {
        if (!this.detailAccountRepo.existsById(id)) {
            throw new NotFoundException(ErrorMessage.DETAILACCOUNT_IS_NOT_FOUND_BY_ID + id);
        }

        DetailAccount detailAccount = this.detailAccountRepo.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.DETAILACCOUNT_IS_NOT_FOUND_BY_ID + id));
        return this.modelMapper.map(detailAccount, DetailAccountDTO.class);
    }

    @Override
    public DetailAccountDTO createDetailAccount(DetailAccountDTO detailAccountDTO) {
        DetailAccount detailAccount = this.modelMapper.map(detailAccountDTO, DetailAccount.class);
        detailAccount = this.detailAccountRepo.save(detailAccount);
        return this.modelMapper.map(detailAccount, DetailAccountDTO.class);
    }

    @Override
    public DetailAccountDTO updateDetailAccount(Long id, DetailAccountDTO detailAccountDTO) {
        if (!this.detailAccountRepo.existsById(id)) {
            throw new NotFoundException(ErrorMessage.DETAILACCOUNT_IS_NOT_FOUND_BY_ID + id);
        }

        DetailAccount detailAccount = this.detailAccountRepo.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.DETAILACCOUNT_IS_NOT_FOUND));
        this.modelMapper.map(detailAccountDTO, detailAccount);
        detailAccount = this.detailAccountRepo.save(detailAccount);
        return this.modelMapper.map(detailAccount, DetailAccountDTO.class);
    }

    @Override
    public void deleteDetailAccount(Long id) {
        if (!this.detailAccountRepo.existsById(id)) {
            throw new NotFoundException(ErrorMessage.DETAILACCOUNT_IS_NOT_FOUND_BY_ID + id);
        }
        this.detailAccountRepo.deleteById(id);
    }
}
