package com.kinto2517.bookstoreapi.unit.service;

import com.kinto2517.bookstoreapi.dto.BorrowDTO;
import com.kinto2517.bookstoreapi.dto.BorrowSaveRequest;
import com.kinto2517.bookstoreapi.dto.BorrowUpdateRequest;
import com.kinto2517.bookstoreapi.entity.Borrow;
import com.kinto2517.bookstoreapi.mapper.BorrowMapper;
import com.kinto2517.bookstoreapi.repository.BookRepository;
import com.kinto2517.bookstoreapi.repository.BorrowRepository;
import com.kinto2517.bookstoreapi.repository.ClientRepository;
import com.kinto2517.bookstoreapi.service.impl.BorrowServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BorrowServiceTest {

    @Mock
    private BorrowRepository borrowRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BorrowServiceImpl borrowService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_shouldReturnBorrowDTOList() {
        List<Borrow> borrows = Arrays.asList(
                new Borrow(1L, null, null, Instant.now(), Instant.now(), true ),
                new Borrow(2L, null, null, Instant.now(), Instant.now(), true)
        );
        when(borrowRepository.findAll()).thenReturn(borrows);

        List<BorrowDTO> borrowDTOS = borrowService.findAll();

        assertEquals(2, borrowDTOS.size());
        assertEquals(1L, borrowDTOS.get(0).id());
        assertEquals(2L, borrowDTOS.get(1).id());
    }

    @Test
    void findByBookId_shouldReturnBorrowDTO() {
        Long bookId = 1L;
        Borrow borrow = new Borrow(1L, bookRepository.findById(bookId).orElse(null), null, Instant.now(), Instant.now(), true);
        when(borrowRepository.findByBookId(bookId)).thenReturn(borrow);

        BorrowDTO borrowDTO = borrowService.findByBookId(bookId);

        assertEquals(bookId, borrowDTO.id());
    }

    @Test
    void findByClientId_shouldReturnBorrowDTO() {
        Long clientId = 1L;
        Borrow borrow = new Borrow(1L, null, clientRepository.findById(clientId).orElse(null), Instant.now(), Instant.now(), true);
        when(borrowRepository.findByClientId(clientId)).thenReturn(borrow);

        BorrowDTO borrowDTO = borrowService.findByClientId(clientId);

        assertEquals(clientId, borrowDTO.id());
    }

    @Test
    void findById_shouldReturnBorrowDTO() {
        Long borrowId = 1L;
        Borrow borrow = new Borrow(borrowId, null, null, Instant.now(), Instant.now(), true);
        when(borrowRepository.findById(borrowId)).thenReturn(Optional.of(borrow));

        BorrowDTO borrowDTO = borrowService.findById(borrowId);

        assertEquals(borrowId, borrowDTO.id());
    }

    /*@Test
    void save_shouldReturnSavedBorrowDTO() {
        BorrowSaveRequest borrowSaveRequest = new BorrowSaveRequest(1L, 1L, Instant.now(), Instant.now());
        when(borrowRepository.existsOverlappingBorrow(anyLong(), any(Instant.class), any(Instant.class))).thenReturn(false);
        when(bookRepository.findById(borrowSaveRequest.bookId())).thenReturn(Optional.ofNullable(null));
        when(clientRepository.findById(borrowSaveRequest.clientId())).thenReturn(Optional.ofNullable(null));
        when(borrowRepository.save(any(Borrow.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BorrowDTO savedBorrowDTO = borrowService.save(borrowSaveRequest);

        assertEquals(1L, savedBorrowDTO.id());
    }*/

    @Test
    void save_shouldThrowExceptionIfBorrowExists() {
        BorrowSaveRequest borrowSaveRequest = new BorrowSaveRequest(1L, 1L, Instant.now(), Instant.now());
        when(borrowRepository.existsOverlappingBorrow(anyLong(), any(Instant.class), any(Instant.class))).thenReturn(true);

        assertThrows(RuntimeException.class, () -> borrowService.save(borrowSaveRequest));
    }

    @Test
    void update_shouldReturnUpdatedBorrowDTO() {
        Long borrowId = 1L;
        BorrowUpdateRequest updatedRequest = new BorrowUpdateRequest(1L, Instant.now(), Instant.now());
        Borrow borrowToUpdate = new Borrow(borrowId, null, null, Instant.now(), Instant.now(), true);
        when(borrowRepository.findById(borrowId)).thenReturn(Optional.of(borrowToUpdate));
        when(borrowRepository.save(any(Borrow.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BorrowDTO updatedBorrowDTO = borrowService.update(borrowId, updatedRequest);

        assertEquals(borrowId, updatedBorrowDTO.id());
    }

    @Test
    void delete_shouldDeleteBorrow() {
        Long borrowId = 1L;
        when(borrowRepository.existsById(borrowId)).thenReturn(true);

        borrowService.delete(borrowId);

        verify(borrowRepository, times(1)).deleteById(borrowId);
    }

    @Test
    void delete_shouldThrowExceptionIfBorrowNotFound() {
        Long borrowId = 1L;
        when(borrowRepository.existsById(borrowId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> borrowService.delete(borrowId));
    }
}
