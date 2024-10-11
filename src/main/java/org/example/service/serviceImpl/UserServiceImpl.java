package org.example.service.serviceImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.ErrorMessage;
import org.example.constant.LogMessage;
import org.example.dto.UserDTO;
import org.example.entity.User;
import org.example.exceptions.exception.NotFoundException;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        log.info(LogMessage.FETCHING_ALL_USERS);

        return userRepo.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

    @Override
    public Optional<UserDTO> getUserById(@NotNull Long id) {
        log.info(LogMessage.FETCHING_USER_BY_ID, id);

        User user = findUserById(id);

        return Optional.ofNullable(this.modelMapper.map(user, UserDTO.class));
    }

    @Override
    @Transactional
    public UserDTO createUser(@Valid UserDTO userDTO) {
        log.info(LogMessage.CREATING_NEW_USER);

        User user = this.modelMapper.map(userDTO, User.class);
        User savedUser = this.userRepo.save(user);

        return this.modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    @Transactional
    public Optional<UserDTO> updateUser(@NotNull Long id, UserDTO userDTO) {
        log.info(LogMessage.UPDATING_USER_BY_ID, id);

        User toUpdate = findUserById(id);
        enhanceWithNewManagementData(toUpdate, userDTO);

        User updatedUser = this.userRepo.save(toUpdate);

        return Optional.ofNullable(this.modelMapper.map(updatedUser, UserDTO.class));
    }

    @Override
    @Transactional
    public void deleteUser(@NotNull Long id) {
        log.info(LogMessage.DELETING_USER_BY_ID, id);

        if (!this.userRepo.existsById(id)) {
            throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + id);
        }

        this.userRepo.deleteById(id);
    }

    private User findUserById(@NotNull Long id) {
        return this.userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + id));
    }

    private void enhanceWithNewManagementData(User toUpdate, UserDTO userDTO) {
        toUpdate.setEmail(userDTO.getEmail());
        toUpdate.setUsername(userDTO.getUsername());
        toUpdate.setPassword(userDTO.getPassword());
    }
}
