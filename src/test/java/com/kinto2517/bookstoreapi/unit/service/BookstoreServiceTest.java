package com.kinto2517.bookstoreapi.unit.service;

import com.kinto2517.bookstoreapi.dto.BookstoreDTO;
import com.kinto2517.bookstoreapi.dto.BookstoreSaveRequest;
import com.kinto2517.bookstoreapi.entity.Bookstore;
import com.kinto2517.bookstoreapi.mapper.BookstoreMapper;
import com.kinto2517.bookstoreapi.repository.BookstoreRepository;
import com.kinto2517.bookstoreapi.service.impl.BookstoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BookstoreServiceTest {

    @Mock
    private BookstoreRepository bookstoreRepository;

    @InjectMocks
    private BookstoreServiceImpl bookstoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllBookstores_shouldReturnBookstoreDTOList() {
        List<Bookstore> bookstores = Arrays.asList(
                new Bookstore(1L, "Bookstore 1", "Address 1", "123456789", null),
                new Bookstore(2L, "Bookstore 2", "Address 2", "987654321", null)
        );
        when(bookstoreRepository.findAll()).thenReturn(bookstores);

        List<BookstoreDTO> bookstoreDTOS = bookstoreService.findAllBookstores();

        assertEquals(2, bookstoreDTOS.size());
        assertEquals("Bookstore 1", bookstoreDTOS.get(0).name());
        assertEquals("Bookstore 2", bookstoreDTOS.get(1).name());
    }

    @Test
    void findById_shouldReturnBookstoreDTO() {
        Long bookstoreId = 1L;
        Bookstore bookstore = new Bookstore(bookstoreId, "Bookstore 1", "Address 1", "123456789", null);
        when(bookstoreRepository.findById(bookstoreId)).thenReturn(Optional.of(bookstore));

        BookstoreDTO bookstoreDTO = bookstoreService.findById(bookstoreId);

        assertEquals(bookstoreId, bookstoreDTO.id());
        assertEquals("Bookstore 1", bookstoreDTO.name());
    }

    @Test
    void saveBookstore_shouldReturnSavedBookstoreDTO() {
        BookstoreSaveRequest bookstoreSaveRequest = new BookstoreSaveRequest("Bookstore 1", "Address 1", "123456789");
        when(bookstoreRepository.save(any(Bookstore.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookstoreDTO savedBookstoreDTO = bookstoreService.save(bookstoreSaveRequest);

        assertEquals("Bookstore 1", savedBookstoreDTO.name());
    }

    @Test
    void updateBookstore_shouldReturnUpdatedBookstoreDTO() {
        Long bookstoreId = 1L;
        BookstoreSaveRequest updatedRequest = new BookstoreSaveRequest("Updated Bookstore", "Updated Address", "987654321");
        Bookstore bookstoreToUpdate = new Bookstore(bookstoreId, "Bookstore 1", "Address 1", "123456789", null);
        when(bookstoreRepository.findById(bookstoreId)).thenReturn(Optional.of(bookstoreToUpdate));
        when(bookstoreRepository.save(any(Bookstore.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookstoreDTO updatedBookstoreDTO = bookstoreService.update(bookstoreId, updatedRequest);

        assertEquals(bookstoreId, updatedBookstoreDTO.id());
        assertEquals("Updated Bookstore", updatedBookstoreDTO.name());
    }

    @Test
    void deleteBookstore_shouldDeleteBookstore() {
        Long bookstoreId = 1L;
        when(bookstoreRepository.existsById(bookstoreId)).thenReturn(true);

        bookstoreService.delete(bookstoreId);

        verify(bookstoreRepository, times(1)).deleteById(bookstoreId);
    }

    @Test
    void deleteBookstore_shouldThrowExceptionIfBookstoreNotFound() {
        Long bookstoreId = 1L;
        when(bookstoreRepository.existsById(bookstoreId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> bookstoreService.delete(bookstoreId));
    }
}
