package com.kinto2517.bookstoreapi.unit.controller;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.kinto2517.bookstoreapi.controller.BookController;
import com.kinto2517.bookstoreapi.dto.BookDTO;
import com.kinto2517.bookstoreapi.dto.BookSaveRequest;
import com.kinto2517.bookstoreapi.service.BookService;
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

class BookControllerTest {

    @Mock
    private BookService bookService;
    @InjectMocks
    private BookController bookController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MockMvc mockMvc;

    public BookControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void getAllBooks_shouldReturnAllBooks() throws Exception {
        when(bookService.findAllBooks()).thenReturn(Arrays.asList(
                new BookDTO(1L, "Book 1", "Author 1", Instant.now(), "Bookstore"),
                new BookDTO(2L, "Book 2", "Author 2", Instant.now(), "Bookstore")
        ));

        mockMvc.perform(get("/api/v1/books/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].title").value("Book 2"));

        verify(bookService, times(1)).findAllBooks();
    }

    @Test
    void saveBook_shouldReturnSavedBook() throws Exception {
        BookSaveRequest bookSaveRequest = new BookSaveRequest("Book 1", "Author 1", Instant.now(), "Bookstore");
        when(bookService.save(any(BookSaveRequest.class))).thenReturn(new BookDTO(1L, "Book 1", "Author 1", Instant.now(), "Bookstore"));

        mockMvc.perform(post("/api/v1/books/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookSaveRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andExpect(jsonPath("$.author").value("Author 1"));

        verify(bookService, times(1)).save(any(BookSaveRequest.class));
    }

    @Test
    void getAllBooksByAuthor_shouldReturnBooksByAuthor() throws Exception {
        String author = "Author 1";
        when(bookService.findByAuthor(author)).thenReturn(Arrays.asList(
                new BookDTO(1L, "Book 1", author, Instant.now(), "Bookstore"),
                new BookDTO(2L, "Book 2", author, Instant.now(), "Bookstore")
        ));

        mockMvc.perform(get("/api/v1/books/allbyauthor/{author}", author)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].title").value("Book 2"));

        verify(bookService, times(1)).findByAuthor(author);
    }

    @Test
    void deleteBook_shouldDeleteBook() throws Exception {
        Long bookId = 1L;

        mockMvc.perform(delete("/api/v1/books/delete/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookService, times(1)).delete(bookId);
    }


}
