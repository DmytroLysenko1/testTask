package org.example.controller;

import org.example.dto.UserDTO;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserDTO userDTO = new UserDTO();

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO(1L, "John Doe", "john.doe@example.com", "password");
    }

    @Test
    public void test_getAllUsers_returnsOk() throws Exception {
        List<UserDTO> users = List.of(
                new UserDTO(1L, "John Doe", "john.doe@example.com", "password"),
                new UserDTO(2L, "Jane Doe", "jane.doe@example.com", "password")
        );
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].username").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].username").value("Jane Doe"))
                .andExpect(jsonPath("$[1].email").value("jane.doe@example.com"));
    }

    @Test
    public void test_getUserById_returnsOk() throws Exception {
        when(userService.getUserById(any(Long.class))).thenReturn(Optional.of(this.userDTO));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.password").value("password"));
    }

    @Test
    public void test_getUserById_returnsNotFound() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_createUser_returnsCreated() throws Exception {
        when(userService.createUser(any(UserDTO.class))).thenReturn(this.userDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "username": "John Doe",
                    "email": "john.doe@example.com",
                    "password": "password"
                }
                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.password").value("password"));
    }

    @Test
    public void test_createUser_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
                "username": "",
                "email": "invalid-email",
                "password": "short"
            }
            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_updateUser_returnsOk() throws Exception {
        when(userService.updateUser(anyLong(), any(UserDTO.class))).thenReturn(Optional.of(this.userDTO));

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "username": "John Doe",
                    "email": "john.doe@example.com",
                    "password": "password"
                }
                """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.password").value("password"));
    }

    @Test
    public void test_updateUser_returnsNotFound() throws Exception {
        when(userService.updateUser(anyLong(), any(UserDTO.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
                "username": "John Doe",
                "email": "john.doe@example.com",
                "password": "password"
            }
            """))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_deleteUser_returnsNoContent() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }
}