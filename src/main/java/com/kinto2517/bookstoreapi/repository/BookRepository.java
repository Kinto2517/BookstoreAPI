package com.kinto2517.bookstoreapi.repository;

import com.kinto2517.bookstoreapi.entity.Book;
import com.kinto2517.bookstoreapi.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.bookstore.name = ?1")
    List<Book> findByBookstoreName(String bookstoreName);

    @Query("SELECT b FROM Book b WHERE b.title = ?1")
    List<Book> findByTitle(String title);

    @Query("SELECT b FROM Book b WHERE b.author = ?1")
    List<Book> findByAuthor(String author);


}