package com.kinto2517.bookstoreapi.service.impl;

import com.kinto2517.bookstoreapi.dto.BookDTO;
import com.kinto2517.bookstoreapi.dto.BookSaveRequest;
import com.kinto2517.bookstoreapi.entity.Book;
import com.kinto2517.bookstoreapi.entity.Bookstore;
import com.kinto2517.bookstoreapi.mapper.BookMapper;
import com.kinto2517.bookstoreapi.repository.BookRepository;
import com.kinto2517.bookstoreapi.repository.BookstoreRepository;
import com.kinto2517.bookstoreapi.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookstoreRepository bookstoreRepository;

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookstoreRepository bookstoreRepository) {
        this.bookRepository = bookRepository;
        this.bookstoreRepository = bookstoreRepository;
    }


    @Override
    public List<BookDTO> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return BookMapper.INSTANCE.booksToBookDTOs(books);
    }

    @Override
    public List<BookDTO> findByBookstoreName(String bookstoreName) {
        List<Book> books = bookRepository.findByBookstoreName(bookstoreName);
        return BookMapper.INSTANCE.booksToBookDTOs(books);
    }

    @Override
    public List<BookDTO> findByTitle(String title) {
        List<Book> books = bookRepository.findByTitle(title);
        return BookMapper.INSTANCE.booksToBookDTOs(books);
    }

    @Override
    public List<BookDTO> findByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor(author);
        return BookMapper.INSTANCE.booksToBookDTOs(books);
    }

    @Override
    public BookDTO findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();
        return BookMapper.INSTANCE.bookToBookDTO(book);
    }

    @Override
    public BookDTO save(BookSaveRequest bookSaveRequest) {
        Book book = BookMapper.INSTANCE.bookSaveRequestToBook(bookSaveRequest);
        Bookstore bookstore = bookstoreRepository.findByBookstoreName(bookSaveRequest.bookstoreName());
        logger.info("bookstore: {}", bookstore.getName());
        book.setBookstore(bookstore);
        Book savedBook = bookRepository.save(book);
        return BookMapper.INSTANCE.bookToBookDTO(savedBook);
    }

    @Override
    public BookDTO update(Long id, BookSaveRequest bookSaveRequest) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(bookSaveRequest.title());
        book.setAuthor(bookSaveRequest.author());
        book.setPublishDate(bookSaveRequest.publishDate());
        Book updatedBook = bookRepository.save(book);
        return BookMapper.INSTANCE.bookToBookDTO(updatedBook);
    }

    @Override
    public void delete(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new RuntimeException("Book not found");
        }
    }
}
