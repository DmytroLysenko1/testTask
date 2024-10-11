package org.example.service;

import org.example.dto.UserDTO;
import org.example.entity.User;
import org.example.enums.Role;
import org.example.exceptions.exception.NotFoundException;
import org.example.repository.UserRepository;
import org.example.service.serviceImpl.UserServiceImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        this.user = new User(1L, "testuser", "test@example.com", Role.USER, "password123");
        this.userDTO = new UserDTO();
        this.userDTO.setUsername("testuser");
        this.userDTO.setEmail("test@example.com");
        this.userDTO.setPassword("password123");
    }

    @Test
    void testGetAllUsers() {
        when(this.userRepository.findAll()).thenReturn(List.of(this.user));
        when(this.modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(this.userDTO);

        List<UserDTO> users = this.userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(this.userDTO.getUsername(), users.get(0).getUsername());
        verify(this.userRepository, times(1)).findAll();
    }
    @Test
    void testGetAllUsers_EmptyRepository() {
        when(this.userRepository.findAll()).thenReturn(List.of());

        List<UserDTO> users = this.userService.getAllUsers();

        assertNotNull(users);
        assertEquals(0, users.size());
        verify(this.userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        when(this.userRepository.findById(1L)).thenReturn(Optional.of(this.user));
        when(this.modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(this.userDTO);

        Optional<UserDTO> result = this.userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(this.userDTO.getUsername(), result.get().getUsername());
        verify(this.userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(this.userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> this.userService.getUserById(1L));

        assertEquals("User not found by id: 1", exception.getMessage());
        verify(this.userRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateUser() {
        when(this.modelMapper.map(any(UserDTO.class), eq(User.class))).thenReturn(this.user);
        when(this.userRepository.save(any(User.class))).thenReturn(this.user);
        when(this.modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(this.userDTO);

        UserDTO result = this.userService.createUser(this.userDTO);

        assertNotNull(result);
        assertEquals(this.userDTO.getUsername(), result.getUsername());
        verify(this.userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser() {
        when(this.userRepository.findById(1L)).thenReturn(Optional.of(this.user));
        when(this.userRepository.save(any(User.class))).thenReturn(this.user);
        when(this.modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(this.userDTO);

        Optional<UserDTO> result = this.userService.updateUser(1L, this.userDTO);

        assertNotNull(result);
        assertEquals(this.userDTO.getUsername(), result.get().getUsername());
        verify(this.userRepository, times(1)).findById(1L);
        verify(this.userRepository, times(1)).save(any(User.class));
    }
    @Test
    void testUpdateUser_UserNotFound() {
        when(this.userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> this.userService.updateUser(1L, this.userDTO));

        assertEquals("User not found by id: 1", exception.getMessage());
        verify(this.userRepository, times(1)).findById(1L);
        verify(this.userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        when(this.userRepository.existsById(1L)).thenReturn(true);

        this.userService.deleteUser(1L);

        verify(this.userRepository, times(1)).existsById(1L);
        verify(this.userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(this.userRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(NotFoundException.class, () -> this.userService.deleteUser(1L));

        assertEquals("User not found by id: 1", exception.getMessage());
        verify(this.userRepository, times(1)).existsById(1L);
        verify(this.userRepository, times(0)).deleteById(1L);
    }
}
