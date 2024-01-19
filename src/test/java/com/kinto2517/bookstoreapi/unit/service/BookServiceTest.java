package com.kinto2517.bookstoreapi.unit.service;

import com.kinto2517.bookstoreapi.dto.BookDTO;
import com.kinto2517.bookstoreapi.dto.BookSaveRequest;
import com.kinto2517.bookstoreapi.entity.Book;
import com.kinto2517.bookstoreapi.entity.Bookstore;
import com.kinto2517.bookstoreapi.mapper.BookMapper;
import com.kinto2517.bookstoreapi.repository.BookRepository;
import com.kinto2517.bookstoreapi.repository.BookstoreRepository;
import com.kinto2517.bookstoreapi.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookstoreRepository bookstoreRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllBooks_shouldReturnBookDTOList() {
        List<Book> books = Arrays.asList(
                new Book(1L, "Book 1", "Author 1", Instant.now(), new Bookstore(), null),
                new Book(2L, "Book 2", "Author 2", Instant.now(), new Bookstore(), null)
        );
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> bookDTOS = bookService.findAllBooks();

        assertEquals(2, bookDTOS.size());
        assertEquals("Book 1", bookDTOS.get(0).title());
        assertEquals("Book 2", bookDTOS.get(1).title());
    }

    @Test
    void findByBookstoreName_shouldReturnBookDTOList() {
        String bookstoreName = "Bookstore";
        List<Book> books = Arrays.asList(
                new Book(1L, "Book 1", "Author 1", Instant.now(), new Bookstore(), null),
                new Book(2L, "Book 2", "Author 2", Instant.now(), new Bookstore(), null)
        );
        when(bookRepository.findByBookstoreName(bookstoreName)).thenReturn(books);

        List<BookDTO> bookDTOS = bookService.findByBookstoreName(bookstoreName);

        assertEquals(2, bookDTOS.size());
        assertEquals("Book 1", bookDTOS.get(0).title());
        assertEquals("Book 2", bookDTOS.get(1).title());
    }

    @Test
    void findByTitle_shouldReturnBookDTOList() {
        String title = "Book 1";
        List<Book> books = Arrays.asList(
                new Book(1L, title, "Author 1", Instant.now(), new Bookstore(), null),
                new Book(2L, title, "Author 2", Instant.now(), new Bookstore(), null)
        );
        when(bookRepository.findByTitle(title)).thenReturn(books);

        List<BookDTO> bookDTOS = bookService.findByTitle(title);

        assertEquals(2, bookDTOS.size());
        assertEquals(title, bookDTOS.get(0).title());
        assertEquals(title, bookDTOS.get(1).title());
    }

    @Test
    void findByAuthor_shouldReturnBookDTOList() {
        String author = "Author 1";
        List<Book> books = Arrays.asList(
                new Book(1L, "Book 1", author, Instant.now(), new Bookstore(), null),
                new Book(2L, "Book 2", author, Instant.now(), new Bookstore(), null)
        );
        when(bookRepository.findByAuthor(author)).thenReturn(books);

        List<BookDTO> bookDTOS = bookService.findByAuthor(author);

        assertEquals(2, bookDTOS.size());
        assertEquals(author, bookDTOS.get(0).author());
        assertEquals(author, bookDTOS.get(1).author());
    }

    @Test
    void findById_shouldReturnBookDTO() {
        Long bookId = 1L;
        Book book = new Book(bookId, "Book 1", "Author 1", Instant.now(), new Bookstore(), null);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        BookDTO bookDTO = bookService.findById(bookId);

        assertEquals(bookId, bookDTO.id());
        assertEquals("Book 1", bookDTO.title());
    }

    @Test
    void updateBook_shouldReturnUpdatedBookDTO() {
        Long bookId = 1L;
        BookSaveRequest updatedRequest = new BookSaveRequest("Updated Book", "Updated Author", Instant.now(), "Bookstore");
        Book bookToUpdate = new Book(bookId, "Book 1", "Author 1", Instant.now(), new Bookstore(), null);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookToUpdate));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookDTO updatedBookDTO = bookService.update(bookId, updatedRequest);

        assertEquals(bookId, updatedBookDTO.id());
        assertEquals("Updated Book", updatedBookDTO.title());
    }

    @Test
    void deleteBook_shouldDeleteBook() {
        Long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(true);

        bookService.delete(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void deleteBook_shouldThrowExceptionIfBookNotFound() {
        Long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> bookService.delete(bookId));
    }

}
