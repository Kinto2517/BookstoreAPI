package com.kinto2517.bookstoreapi.repository;

import com.kinto2517.bookstoreapi.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Map;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    @Query("SELECT b FROM Borrow b WHERE b.book.id = ?1")
    Borrow findByBookId(Long bookId);

    @Query("SELECT b FROM Borrow b WHERE b.client.id = ?1")
    Borrow findByClientId(Long clientId);

    @Query("SELECT COUNT(b) > 0 FROM Borrow b " +
            "WHERE b.book.id = :bookId " +
            "AND b.endDate >= :startDate AND b.startDate <= :endDate " +
            "AND b.borrowed = true")
    boolean existsOverlappingBorrow(Long bookId,
                                    Instant startDate,
                                    Instant endDate);

    @Query("SELECT COUNT(b) FROM Borrow b " +
            "WHERE (b.startDate >= :startDate AND b.startDate <= :endDate) " +
            "OR (b.endDate >= :startDate AND b.endDate <= :endDate) " +
            "OR (:startDate >= b.startDate AND :startDate <= b.endDate) " +
            "OR (:endDate >= b.startDate AND :endDate <= b.endDate) " +
            "AND b.borrowed = true")
    Long countBorrowedBooks(Instant startDate, Instant endDate);
}