package com.kinto2517.bookstoreapi.controller;

import com.kinto2517.bookstoreapi.dto.BookDTO;
import com.kinto2517.bookstoreapi.dto.BookSaveRequest;
import com.kinto2517.bookstoreapi.entity.Client;
import com.kinto2517.bookstoreapi.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping("/allbyname/{bookstoreName}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<BookDTO>> getAllBooksByBookstoreName(@PathVariable String bookstoreName) {

        return ResponseEntity.ok(bookService.findByBookstoreName(bookstoreName));
    }

    @GetMapping("/allbytitle/{title}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<BookDTO>> getAllBooksByTitle(@PathVariable String title) {

        return ResponseEntity.ok(bookService.findByTitle(title));
    }

    @GetMapping("/allbyauthor/{author}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<BookDTO>> getAllBooksByAuthor(@PathVariable String author) {

        return ResponseEntity.ok(bookService.findByAuthor(author));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookSaveRequest bookSaveRequest) {
        return ResponseEntity.ok(bookService.save(bookSaveRequest));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookSaveRequest bookSaveRequest) {
        return ResponseEntity.ok(bookService.update(id, bookSaveRequest));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
