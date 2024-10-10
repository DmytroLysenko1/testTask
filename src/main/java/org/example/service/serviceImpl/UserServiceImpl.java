package org.example.service.serviceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.ErrorMessage;
import org.example.dto.UserDTO;
import org.example.entity.User;
import org.example.exceptions.exception.NotFoundException;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepo.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        log.info("Fetching user with id: {}", id);
        User user = findUserById(id);
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    @Transactional
    public UserDTO createUser(@Valid UserDTO userDTO) {
        log.info("Creating a new user");
        User user = this.modelMapper.map(userDTO, User.class);
        User savedUser = this.userRepo.save(user);
        return this.modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        log.info("Updating user with id: {}", id);
        User toUpdate = findUserById(id);
        enhanceWithNewManagementData(toUpdate, userDTO);
        User updatedUser = this.userRepo.save(toUpdate);
        return this.modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        if (!this.userRepo.existsById(id)) {
            throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + id);
        }
        this.userRepo.deleteById(id);
    }

    private User findUserById(Long id) {
        return this.userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + id));
    }

    private void enhanceWithNewManagementData(User toUpdate, UserDTO userDTO) {
        toUpdate.setEmail(userDTO.getEmail());
        toUpdate.setUsername(userDTO.getUsername());
        toUpdate.setPassword(userDTO.getPassword());
    }
}
