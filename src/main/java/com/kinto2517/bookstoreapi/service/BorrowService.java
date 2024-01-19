package com.kinto2517.bookstoreapi.service;

import com.kinto2517.bookstoreapi.dto.BorrowDTO;
import com.kinto2517.bookstoreapi.dto.BorrowSaveRequest;
import com.kinto2517.bookstoreapi.dto.BorrowUpdateRequest;
import com.kinto2517.bookstoreapi.entity.Borrow;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface BorrowService {

    List<BorrowDTO> findAll();

    BorrowDTO findByBookId(Long bookId);

    BorrowDTO findByClientId(Long clientId);

    BorrowDTO findById(Long id);

    BorrowDTO save(BorrowSaveRequest borrowSaveRequest);

    BorrowDTO update(Long id, BorrowUpdateRequest borrowUpdateRequest);

    void delete(Long id);

    Map<String, Long> getDailyReport(Instant startDate, Instant endDate);
}
