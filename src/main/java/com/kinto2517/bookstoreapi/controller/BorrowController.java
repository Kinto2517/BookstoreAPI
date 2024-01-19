package com.kinto2517.bookstoreapi.controller;

import com.kinto2517.bookstoreapi.dto.BorrowDTO;
import com.kinto2517.bookstoreapi.dto.BorrowSaveRequest;
import com.kinto2517.bookstoreapi.dto.BorrowUpdateRequest;
import com.kinto2517.bookstoreapi.entity.Borrow;
import com.kinto2517.bookstoreapi.entity.Client;
import com.kinto2517.bookstoreapi.service.BorrowService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/borrows")
public class BorrowController {

    private final BorrowService borrowService;

    private static final Logger logger = LoggerFactory.getLogger(BorrowController.class);

    @Autowired
    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<BorrowDTO>> getAllBorrows() {
        return ResponseEntity.ok(borrowService.findAll());
    }

    @GetMapping("/bybook/{bookId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<BorrowDTO> getBorrowByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(borrowService.findByBookId(bookId));
    }

    @GetMapping("/byclient/{clientId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<BorrowDTO> getBorrowByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(borrowService.findByClientId(clientId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<BorrowDTO> getBorrowById(@PathVariable Long id) {
        return ResponseEntity.ok(borrowService.findById(id));
    }

    @GetMapping("/daily-report")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Map<String, Long>> getDailyReport(
            @RequestParam Instant startDate,
            @RequestParam Instant endDate) {
        return ResponseEntity.ok().body(borrowService.getDailyReport(startDate, endDate));
    }


    @PostMapping("/save")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<BorrowDTO> saveBorrow(
            @RequestBody BorrowSaveRequest borrowSaveRequest,
            Authentication authentication) {

        Client client = (Client) authentication.getPrincipal();
        logger.info("Client: {}", client);

        if (!client.getId().equals(borrowSaveRequest.clientId())) {
            logger.error("Client id: {} is not equal to request client id: {}", client.getId(), borrowSaveRequest.clientId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        logger.info("Borrow save request: {}", borrowSaveRequest);

        return ResponseEntity.ok(borrowService.save(borrowSaveRequest));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BorrowDTO> updateBorrow(
            @PathVariable Long id,
            @RequestBody BorrowUpdateRequest borrowUpdateRequest,
            Authentication authentication) {

        Client client = (Client) authentication.getPrincipal();

        if (!client.getId().equals(borrowUpdateRequest.clientId())) {
            logger.error("Client id: {} is not equal to request client id: {}", client.getId(), borrowUpdateRequest.clientId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(borrowService.update(id, borrowUpdateRequest));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBorrow(@PathVariable Long id) {
        borrowService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
