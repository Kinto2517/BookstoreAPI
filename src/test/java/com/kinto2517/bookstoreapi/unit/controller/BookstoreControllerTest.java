package com.kinto2517.bookstoreapi.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinto2517.bookstoreapi.controller.BookController;
import com.kinto2517.bookstoreapi.controller.BookstoreController;
import com.kinto2517.bookstoreapi.dto.BookstoreDTO;
import com.kinto2517.bookstoreapi.dto.BookstoreSaveRequest;
import com.kinto2517.bookstoreapi.service.BookstoreService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookstoreControllerTest {


    private final MockMvc mockMvc;

    @Mock
    private BookstoreService bookstoreService;

    @InjectMocks
    private BookstoreController bookstoreController;

    private ObjectMapper objectMapper = new ObjectMapper();

    public BookstoreControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookstoreController).build();
    }

    @Test
    void getAllBookstores_shouldReturnAllBookstores() throws Exception {
        when(bookstoreService.findAllBookstores()).thenReturn(Arrays.asList(
                new BookstoreDTO(1L, "Bookstore 1", "Address 1", "123456789"),
                new BookstoreDTO(2L, "Bookstore 2", "Address 2", "987654321")
        ));

        mockMvc.perform(get("/api/v1/bookstores/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Bookstore 1"))
                .andExpect(jsonPath("$[1].name").value("Bookstore 2"));

        verify(bookstoreService, times(1)).findAllBookstores();
    }

    @Test
    void getBookstoreById_shouldReturnBookstoreById() throws Exception {
        Long bookstoreId = 1L;
        when(bookstoreService.findById(bookstoreId)).thenReturn(
                new BookstoreDTO(bookstoreId, "Bookstore 1", "Address 1", "123456789")
        );

        mockMvc.perform(get("/api/v1/bookstores/all/{id}", bookstoreId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Bookstore 1"));

        verify(bookstoreService, times(1)).findById(bookstoreId);
    }

    @Test
    void saveBookstore_shouldReturnSavedBookstore() throws Exception {
        BookstoreSaveRequest bookstoreSaveRequest = new BookstoreSaveRequest("Bookstore 1", "Address 1", "123456789");
        when(bookstoreService.save(any(BookstoreSaveRequest.class)))
                .thenReturn(new BookstoreDTO(1L, "Bookstore 1", "Address 1", "123456789"));

        mockMvc.perform(post("/api/v1/bookstores/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookstoreSaveRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Bookstore 1"));

        verify(bookstoreService, times(1)).save(any(BookstoreSaveRequest.class));
    }

    @Test
    void updateBookstore_shouldReturnUpdatedBookstore() throws Exception {
        Long bookstoreId = 1L;
        BookstoreSaveRequest updatedRequest = new BookstoreSaveRequest("Updated Bookstore", "Updated Address", "987654321");
        when(bookstoreService.update(eq(bookstoreId), any(BookstoreSaveRequest.class)))
                .thenReturn(new BookstoreDTO(bookstoreId, "Updated Bookstore", "Updated Address", "987654321"));

        mockMvc.perform(put("/api/v1/bookstores/update/{id}", bookstoreId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Bookstore"));

        verify(bookstoreService, times(1)).update(bookstoreId, updatedRequest);
    }

    @Test
    void deleteBookstore_shouldDeleteBookstore() throws Exception {
        Long bookstoreId = 1L;

        mockMvc.perform(delete("/api/v1/bookstores/delete/{id}", bookstoreId))
                .andExpect(status().isNoContent());

        verify(bookstoreService, times(1)).delete(bookstoreId);
    }
}
