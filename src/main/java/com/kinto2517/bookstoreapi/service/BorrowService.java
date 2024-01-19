package com.kinto2517.bookstoreapi.service;

import com.kinto2517.bookstoreapi.dto.BorrowDTO;
import com.kinto2517.bookstoreapi.dto.BorrowSaveRequest;
import com.kinto2517.bookstoreapi.dto.BorrowUpdateRequest;
import com.kinto2517.bookstoreapi.entity.Borrow;

import java.util.List;

public interface BorrowService {

    List<BorrowDTO> findAll();

    BorrowDTO findByBookId(Long bookId);

    BorrowDTO findByClientId(Long clientId);

    BorrowDTO findById(Long id);

    BorrowDTO save(BorrowSaveRequest borrowSaveRequest);

    BorrowDTO update(Long id, BorrowUpdateRequest borrowUpdateRequest);

    void delete(Long id);


}
