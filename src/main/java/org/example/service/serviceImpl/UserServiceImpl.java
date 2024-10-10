package org.example.service.serviceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        return this.userRepo.findAll().stream()
                .map(user -> this.modelMapper.map(user, UserDTO.class))
                .toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = this.userRepo.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + id));
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    @Transactional
    public UserDTO createUser(@Valid UserDTO userDTO) {
        User user = this.modelMapper.map(userDTO, User.class);
        User savedUser = this.userRepo.save(user);
        return this.modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User toUpdate = this.modelMapper.map(getUserById(id), User.class);
        enhanceWithNewManagementData(toUpdate, userDTO);
        User updatedUser = this.userRepo.save(toUpdate);
        return this.modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!this.userRepo.existsById(id)) {
            throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + id);
        }
        this.userRepo.deleteById(id);
    }

    private void enhanceWithNewManagementData(User toUpdate, UserDTO userDTO) {
        toUpdate.setEmail(userDTO.getEmail());
        toUpdate.setUsername(userDTO.getUsername());
        toUpdate.setPassword(userDTO.getPassword());
    }
}
