package org.example.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.ErrorMessage;
import org.example.constant.LogMessage;
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
        log.info(LogMessage.FETCHING_ALL_DETAIL_ACCOUNTS);

        return this.detailAccountRepo.findAll().stream()
                .map(detailAccount -> modelMapper.map(detailAccount, DetailAccountDTO.class))
                .toList();
    }

    @Override
    public DetailAccountDTO getDetailAccountById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(ErrorMessage.DETAILACCOUNT_ID_CANNOT_BE_NULL);
        }

        log.info(LogMessage.FETCHING_DETAIL_ACCOUNT_BY_ID, id);

        DetailAccount detailAccount = findDetailAccountById(id);

        return this.modelMapper.map(detailAccount, DetailAccountDTO.class);
    }

    @Override
    public DetailAccountDTO createDetailAccount(DetailAccountDTO detailAccountDTO) {
        if (detailAccountDTO == null) {
            throw new IllegalArgumentException(ErrorMessage.DETAILACCOUNTDTO_CANNOT_BE_NULL);
        }

        log.info(LogMessage.CREATING_NEW_DETAIL_ACCOUNT);

        DetailAccount detailAccount = this.modelMapper.map(detailAccountDTO, DetailAccount.class);

        detailAccount = this.detailAccountRepo.save(detailAccount);

        return this.modelMapper.map(detailAccount, DetailAccountDTO.class);
    }

    @Override
    public DetailAccountDTO updateDetailAccount(Long id, DetailAccountDTO detailAccountDTO) {
        if (detailAccountDTO == null) {
            throw new IllegalArgumentException(ErrorMessage.DETAILACCOUNTDTO_CANNOT_BE_NULL);
        }

        log.info(LogMessage.UPDATING_DETAIL_ACCOUNT, id);

        DetailAccount detailAccount = findDetailAccountById(id);

        this.modelMapper.map(detailAccountDTO, detailAccount);

        detailAccount = this.detailAccountRepo.save(detailAccount);

        return this.modelMapper.map(detailAccount, DetailAccountDTO.class);
    }

    @Override
    public void deleteDetailAccount(Long id) {
        log.info(LogMessage.DELETING_DETAIL_ACCOUNT, id);

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
