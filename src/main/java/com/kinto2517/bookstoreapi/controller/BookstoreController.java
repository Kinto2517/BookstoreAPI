package com.kinto2517.bookstoreapi.controller;

import com.kinto2517.bookstoreapi.dto.BookstoreDTO;
import com.kinto2517.bookstoreapi.dto.BookstoreSaveRequest;
import com.kinto2517.bookstoreapi.service.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookstores")
public class BookstoreController {

    private final BookstoreService bookstoreService;

    @Autowired
    public BookstoreController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<BookstoreDTO>> getAllBookstores() {
        return ResponseEntity.ok(bookstoreService.findAllBookstores());
    }

    @GetMapping("/all/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<BookstoreDTO> getBookstoreById(@PathVariable Long id) {
        return ResponseEntity.ok(bookstoreService.findById(id));
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<BookstoreDTO> saveBookstore(@RequestBody BookstoreSaveRequest bookstoreSaveRequest) {
        return ResponseEntity.ok(bookstoreService.save(bookstoreSaveRequest));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookstoreDTO> updateBookstore(@PathVariable Long id, @RequestBody BookstoreSaveRequest bookstoreSaveRequest) {
        return ResponseEntity.ok(bookstoreService.update(id, bookstoreSaveRequest));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBookstore(@PathVariable Long id) {
        bookstoreService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
