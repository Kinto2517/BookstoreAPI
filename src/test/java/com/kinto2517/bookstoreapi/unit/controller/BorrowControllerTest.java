package com.kinto2517.bookstoreapi.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinto2517.bookstoreapi.controller.BorrowController;
import com.kinto2517.bookstoreapi.dto.BorrowDTO;
import com.kinto2517.bookstoreapi.dto.BorrowSaveRequest;
import com.kinto2517.bookstoreapi.dto.BorrowUpdateRequest;
import com.kinto2517.bookstoreapi.service.BorrowService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BorrowControllerTest {

    private final MockMvc mockMvc;

    @Mock
    private BorrowService borrowService;

    @InjectMocks
    private BorrowController borrowController;

    private ObjectMapper objectMapper = new ObjectMapper();

    public BorrowControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(borrowController).build();
    }

    @Test
    void getAllBorrows_shouldReturnAllBorrows() throws Exception {
        when(borrowService.findAll()).thenReturn(Arrays.asList(
                new BorrowDTO(1L, 1L, 1L, Instant.now(), Instant.now().plusSeconds(3600)),
                new BorrowDTO(2L, 2L, 2L, Instant.now(), Instant.now().plusSeconds(7200))
        ));

        mockMvc.perform(get("/api/v1/borrows/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(borrowService, times(1)).findAll();
    }

    @Test
    void getBorrowByBookId_shouldReturnBorrowByBookId() throws Exception {
        Long bookId = 1L;
        when(borrowService.findByBookId(bookId)).thenReturn(
                new BorrowDTO(1L, bookId, 1L, Instant.now(), Instant.now().plusSeconds(3600))
        );

        mockMvc.perform(get("/api/v1/borrows/bybook/{bookId}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookId").value(bookId));

        verify(borrowService, times(1)).findByBookId(bookId);
    }

    @Test
    void getBorrowByClientId_shouldReturnBorrowByClientId() throws Exception {
        Long clientId = 1L;
        when(borrowService.findByClientId(clientId)).thenReturn(
                new BorrowDTO(1L, 1L, clientId, Instant.now(), Instant.now().plusSeconds(3600))
        );

        mockMvc.perform(get("/api/v1/borrows/byclient/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.clientId").value(clientId));

        verify(borrowService, times(1)).findByClientId(clientId);
    }

   /* @Test
    void saveBorrow_shouldReturnSavedBorrow() throws Exception {
        BorrowSaveRequest borrowSaveRequest = new BorrowSaveRequest(1L, 1L, Instant.now(), Instant.now().plusSeconds(3600));
        when(borrowService.save(any(BorrowSaveRequest.class)))
                .thenReturn(new BorrowDTO(1L, 1L, 1L, Instant.now(), Instant.now().plusSeconds(3600)));

        mockMvc.perform(post("/api/v1/borrows/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowSaveRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));

        verify(borrowService, times(1)).save(any(BorrowSaveRequest.class));
    }*/

   /* @Test
    void updateBorrow_shouldReturnUpdatedBorrow() throws Exception {
        Long borrowId = 1L;
        BorrowUpdateRequest updatedRequest = new BorrowUpdateRequest(1L, Instant.now(), Instant.now().plusSeconds(7200));
        when(borrowService.update(eq(borrowId), any(BorrowUpdateRequest.class)))
                .thenReturn(new BorrowDTO(borrowId, 1L, 1L, Instant.now(), Instant.now().plusSeconds(7200)));

        mockMvc.perform(put("/api/v1/borrows/update/{id}", borrowId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.endDate").isNotEmpty());

        verify(borrowService, times(1)).update(borrowId, updatedRequest);
    }*/

    @Test
    void deleteBorrow_shouldDeleteBorrow() throws Exception {
        Long borrowId = 1L;

        mockMvc.perform(delete("/api/v1/borrows/delete/{id}", borrowId))
                .andExpect(status().isNoContent());

        verify(borrowService, times(1)).delete(borrowId);
    }
}
