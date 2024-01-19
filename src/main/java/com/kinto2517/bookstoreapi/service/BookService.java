package com.kinto2517.bookstoreapi.service;

import com.kinto2517.bookstoreapi.dto.BookDTO;
import com.kinto2517.bookstoreapi.dto.BookSaveRequest;
import com.kinto2517.bookstoreapi.dto.BookstoreDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> findAllBooks();

    List<BookDTO> findByBookstoreName(String bookstoreName);

    List<BookDTO> findByTitle(String title);

    List<BookDTO> findByAuthor(String author);

    BookDTO findById(Long id);

    BookDTO save(BookSaveRequest bookSaveRequest);

    BookDTO update(Long id, BookSaveRequest bookSaveRequest);

    void delete(Long id);



}
