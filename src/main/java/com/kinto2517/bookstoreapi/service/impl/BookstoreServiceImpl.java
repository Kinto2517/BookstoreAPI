package com.kinto2517.bookstoreapi.service.impl;

import com.kinto2517.bookstoreapi.dto.BookstoreDTO;
import com.kinto2517.bookstoreapi.dto.BookstoreSaveRequest;
import com.kinto2517.bookstoreapi.entity.Bookstore;
import com.kinto2517.bookstoreapi.mapper.BookstoreMapper;
import com.kinto2517.bookstoreapi.repository.BookstoreRepository;
import com.kinto2517.bookstoreapi.service.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookstoreServiceImpl implements BookstoreService {

    private final BookstoreRepository bookstoreRepository;

    @Autowired
    public BookstoreServiceImpl(BookstoreRepository bookstoreRepository) {
        this.bookstoreRepository = bookstoreRepository;
    }

    @Override
    public List<BookstoreDTO> findAllBookstores() {
        List<Bookstore> bookstores = bookstoreRepository.findAll();
        return BookstoreMapper.INSTANCE.bookstoresToBookstoreDTOs(bookstores);
    }

    @Override
    public BookstoreDTO findById(Long id) {
        Bookstore bookstore = bookstoreRepository.findById(id).orElseThrow();
        return BookstoreMapper.INSTANCE.bookstoreToBookstoreDTO(bookstore);
    }

    @Override
    public BookstoreDTO save(BookstoreSaveRequest bookstoreSaveRequest) {
        Bookstore bookstore = BookstoreMapper.INSTANCE.bookstoreSaveRequestToBookstore(bookstoreSaveRequest);
        Bookstore savedBookstore = bookstoreRepository.save(bookstore);
        return BookstoreMapper.INSTANCE.bookstoreToBookstoreDTO(savedBookstore);
    }

    @Override
    public BookstoreDTO update(Long id, BookstoreSaveRequest bookstoreSaveRequest) {
        Bookstore bookstore = BookstoreMapper.INSTANCE.bookstoreSaveRequestToBookstore(bookstoreSaveRequest);
        bookstore.setId(id);
        Bookstore updatedBookstore = bookstoreRepository.save(bookstore);
        return BookstoreMapper.INSTANCE.bookstoreToBookstoreDTO(updatedBookstore);
    }

    @Override
    public void delete(Long id) {
        if (!bookstoreRepository.existsById(id)) {
            throw new RuntimeException("Bookstore with id " + id + " not found");
        }
        bookstoreRepository.deleteById(id);
    }
}
