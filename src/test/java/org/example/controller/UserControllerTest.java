package org.example.controller;

import org.example.dto.UserDTO;
import org.example.service.UserService;
import org.example.utils.ModelUtils;
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

    private static final String BASE_URL = "/users";
    private static final String URL_ID = "/users/1";

    @BeforeEach
    public void setUp() {
        this.userDTO = ModelUtils.getUserDTO();
    }

    @Test
    public void test_getAllUsers_returnsOk() throws Exception {
        List<UserDTO> users = List.of(
                ModelUtils.getUserDTO(),
                ModelUtils.getUserDTO2()
        );
        when(this.userService.getAllUsers()).thenReturn(users);

        this.mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].username").value("Jane Doe"))
                .andExpect(jsonPath("$[1].email").value("jane.doe@example.com"));
    }

    @Test
    public void test_getUserById_returnsOk() throws Exception {
        when(this.userService.getUserById(any(Long.class))).thenReturn(Optional.of(this.userDTO));

        this.mockMvc.perform(get(URL_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.password").value("password"));
    }

    @Test
    public void test_getUserById_returnsNotFound() throws Exception {
        when(this.userService.getUserById(anyLong())).thenReturn(Optional.empty());

        this.mockMvc.perform(get(URL_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_createUser_returnsCreated() throws Exception {
        when(this.userService.createUser(any(UserDTO.class))).thenReturn(this.userDTO);

        this.mockMvc.perform(post(BASE_URL)
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
                .andExpect(jsonPath("$.username").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.password").value("password"));
    }

    @Test
    public void test_createUser_returnsBadRequest() throws Exception {
        this.mockMvc.perform(post(BASE_URL)
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
        when(this.userService.updateUser(anyLong(), any(UserDTO.class))).thenReturn(Optional.of(this.userDTO));

        this.mockMvc.perform(put(URL_ID)
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
                .andExpect(jsonPath("$.username").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.password").value("password"));
    }

    @Test
    public void test_updateUser_returnsNotFound() throws Exception {
        when(this.userService.updateUser(anyLong(), any(UserDTO.class))).thenReturn(Optional.empty());

        this.mockMvc.perform(put(URL_ID)
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
        doNothing().when(this.userService).deleteUser(anyLong());

        this.mockMvc.perform(delete(URL_ID))
                .andExpect(status().isNoContent());
    }
}