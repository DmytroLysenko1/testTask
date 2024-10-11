package org.example.service;

import org.example.dto.UserDTO;
import org.example.exceptions.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(Long id) throws NotFoundException;
    UserDTO createUser(UserDTO userDTO);
    Optional<UserDTO> updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id) throws NotFoundException;
}
