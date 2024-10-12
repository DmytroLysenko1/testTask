package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.constant.TestMessages;
import org.example.dto.DetailAccountDTO;
import org.example.service.DetailAccountService;
import org.example.utils.ModelUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(DetailAccountController.class)
public class DetailAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DetailAccountService detailAccountService;

    private DetailAccountDTO detailAccountDTO = new DetailAccountDTO();
    private static final String BASE_URL = "/detail-account";
    private static final String URL_ID = "/detail-account/1";

    @BeforeEach
    public void setUp() {
        this.detailAccountDTO = ModelUtils.getDetailAccountDTO();
    }

    @Test
    public void test_getAllDetailAccounts_returnsOk() throws Exception {
        when(this.detailAccountService.getAllDetailAccounts())
                .thenReturn(Collections.singletonList(this.detailAccountDTO));

        this.mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id")
                        .value(this.detailAccountDTO.getId()));
    }

    @Test
    public void test_getDetailAccountById_returnsOk() throws Exception {
        when(this.detailAccountService.getDetailAccountById(anyLong()))
                .thenReturn(this.detailAccountDTO);

        this.mockMvc.perform(get(URL_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id")
                        .value(this.detailAccountDTO.getId()));
    }

    @Test
    public void test_createDetailAccount_returnsCreated() throws Exception {
        when(this.detailAccountService.createDetailAccount(any(DetailAccountDTO.class)))
                .thenReturn(this.detailAccountDTO);

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "reportingDate": "2023-10-10",
                    "sum": 1000,
                    "percentage": 5,
                    "discountRate": 2,
                    "totalSum": 1000,
                    "bankAccountId": 1
                }
                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id")
                        .value(this.detailAccountDTO.getId()));
    }

    @Test
    public void test_updateDetailAccount_returnsOk() throws Exception {
        when(this.detailAccountService.updateDetailAccount(anyLong(), any(DetailAccountDTO.class)))
                .thenReturn(this.detailAccountDTO);

        this.mockMvc.perform(put(URL_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "reportingDate": "2023-10-10",
                    "sum": 1000,
                    "percentage": 5,
                    "discountRate": 2,
                    "totalSum": 1000,
                    "bankAccountId": 1
                }
                """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id")
                        .value(this.detailAccountDTO.getId()));
    }

    @Test
    public void test_updateNonExistentDetailAccount_returnsNotFound() throws Exception {
        when(this.detailAccountService.updateDetailAccount(anyLong(), any(DetailAccountDTO.class)))
                .thenThrow(new EntityNotFoundException(TestMessages.DETAIL_ACCOUNT_NOT_FOUND));

        this.mockMvc.perform(put(BASE_URL + "/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
                "reportingDate": "2023-10-10",
                "sum": 1000,
                "percentage": 5,
                "discountRate": 2,
                "totalSum": 1000,
                "bankAccountId": 1
            }
            """))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_deleteDetailAccount_returnsNoContent() throws Exception {
        this.mockMvc.perform(delete(URL_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_deleteNonExistentDetailAccount_returnsNotFound() throws Exception {
        doThrow(new EntityNotFoundException(TestMessages.DETAIL_ACCOUNT_NOT_FOUND))
                .when(this.detailAccountService).deleteDetailAccount(anyLong());

        this.mockMvc.perform(delete(BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }
}
