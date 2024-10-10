package org.example.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class DetailAccountServiceImpl implements DetailAccountService {

    private final DetailAccountRepository detailAccountRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<DetailAccountDTO> getAllDetailAccounts() {
        log.info("Fetching all detail accounts");
        return this.detailAccountRepo.findAll().stream()
                .map(detailAccount -> modelMapper.map(detailAccount, DetailAccountDTO.class))
                .toList();
    }

    @Override
    public DetailAccountDTO getDetailAccountById(Long id) {
        log.info("Fetching detail account with id: {}", id);
        DetailAccount detailAccount = findDetailAccountById(id);
        return this.modelMapper.map(detailAccount, DetailAccountDTO.class);
    }

    @Override
    public DetailAccountDTO createDetailAccount(DetailAccountDTO detailAccountDTO) {
        log.info("Creating a new detail account");
        DetailAccount detailAccount = this.modelMapper.map(detailAccountDTO, DetailAccount.class);
        detailAccount = this.detailAccountRepo.save(detailAccount);
        return this.modelMapper.map(detailAccount, DetailAccountDTO.class);
    }

    @Override
    public DetailAccountDTO updateDetailAccount(Long id, DetailAccountDTO detailAccountDTO) {
        log.info("Updating detail account with id: {}", id);
        DetailAccount detailAccount = findDetailAccountById(id);
        this.modelMapper.map(detailAccountDTO, detailAccount);
        detailAccount = this.detailAccountRepo.save(detailAccount);
        return this.modelMapper.map(detailAccount, DetailAccountDTO.class);
    }

    @Override
    public void deleteDetailAccount(Long id) {
        log.info("Deleting detail account with id: {}", id);
        if (!this.detailAccountRepo.existsById(id)) {
            throw new NotFoundException(ErrorMessage.DETAILACCOUNT_IS_NOT_FOUND_BY_ID + id);
        }
        this.detailAccountRepo.deleteById(id);
    }

    private DetailAccount findDetailAccountById(Long id) {
        return this.detailAccountRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.DETAILACCOUNT_IS_NOT_FOUND_BY_ID + id));
    }
}
