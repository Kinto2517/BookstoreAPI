package com.kinto2517.bookstoreapi.service.impl;

import com.kinto2517.bookstoreapi.dto.BorrowDTO;
import com.kinto2517.bookstoreapi.dto.BorrowSaveRequest;
import com.kinto2517.bookstoreapi.dto.BorrowUpdateRequest;
import com.kinto2517.bookstoreapi.entity.Borrow;
import com.kinto2517.bookstoreapi.mapper.BorrowMapper;
import com.kinto2517.bookstoreapi.repository.BookRepository;
import com.kinto2517.bookstoreapi.repository.BorrowRepository;
import com.kinto2517.bookstoreapi.repository.ClientRepository;
import com.kinto2517.bookstoreapi.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepository borrowRepository;

    private final ClientRepository clientRepository;

    private final BookRepository bookRepository;

    @Autowired
    public BorrowServiceImpl(BorrowRepository borrowRepository, ClientRepository clientRepository, BookRepository bookRepository) {
        this.borrowRepository = borrowRepository;
        this.clientRepository = clientRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BorrowDTO> findAll() {
        List<Borrow> borrows = borrowRepository.findAll();
        return BorrowMapper.INSTANCE.borrowsToBorrowDTOs(borrows);
    }

    @Override
    public BorrowDTO findByBookId(Long bookId) {
        Borrow borrow = borrowRepository.findByBookId(bookId);
        return BorrowMapper.INSTANCE.borrowToBorrowDTO(borrow);
    }

    @Override
    public BorrowDTO findByClientId(Long clientId) {
        Borrow borrow = borrowRepository.findByClientId(clientId);
        return BorrowMapper.INSTANCE.borrowToBorrowDTO(borrow);
    }

    @Override
    public BorrowDTO findById(Long id) {
        Borrow borrow = borrowRepository.findById(id).orElseThrow();
        return BorrowMapper.INSTANCE.borrowToBorrowDTO(borrow);
    }

    @Override
    public BorrowDTO save(BorrowSaveRequest borrowSaveRequest) {
        if (borrowRepository.existsOverlappingBorrow(borrowSaveRequest.bookId(), borrowSaveRequest.startDate(), borrowSaveRequest.endDate())) {
            throw new RuntimeException("Borrow already exists");
        }
        Borrow borrow = new Borrow();
        borrow.setBook(bookRepository.findById(borrowSaveRequest.bookId()).orElseThrow());
        borrow.setClient(clientRepository.findById(borrowSaveRequest.clientId()).orElseThrow());
        borrow.setStartDate(borrowSaveRequest.startDate());
        borrow.setEndDate(borrowSaveRequest.endDate());
        borrow.setBorrowed(true);
        Borrow savedBorrow = borrowRepository.save(borrow);
        return BorrowMapper.INSTANCE.borrowToBorrowDTO(savedBorrow);
    }

    @Override
    public BorrowDTO update(Long id, BorrowUpdateRequest borrowUpdateRequest) {
        Borrow borrow = borrowRepository.findById(id).orElseThrow();
        borrow.setStartDate(borrowUpdateRequest.startDate());
        borrow.setEndDate(borrowUpdateRequest.endDate());
        Borrow updatedBorrow = borrowRepository.save(borrow);
        return BorrowMapper.INSTANCE.borrowToBorrowDTO(updatedBorrow);
    }

    @Override
    public void delete(Long id) {
        if (!borrowRepository.existsById(id)) {
            throw new RuntimeException("Borrow does not exist");
        }
        borrowRepository.deleteById(id);
    }
}
