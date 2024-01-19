package com.kinto2517.bookstoreapi.service;

import com.kinto2517.bookstoreapi.dto.BookstoreDTO;
import com.kinto2517.bookstoreapi.dto.BookstoreSaveRequest;

import java.util.List;

public interface BookstoreService {

    List<BookstoreDTO> findAllBookstores();

    BookstoreDTO findById(Long id);

    BookstoreDTO save(BookstoreSaveRequest bookstoreSaveRequest);

    BookstoreDTO update(Long id, BookstoreSaveRequest bookstoreSaveRequest);

    void delete(Long id);
}
