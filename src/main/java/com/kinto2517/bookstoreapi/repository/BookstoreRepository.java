package com.kinto2517.bookstoreapi.repository;

import com.kinto2517.bookstoreapi.entity.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {
    @Query("SELECT b FROM Bookstore b WHERE b.name = ?1")
    Bookstore findByBookstoreName(String bookstoreName);
}