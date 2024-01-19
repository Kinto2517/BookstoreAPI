package com.kinto2517.bookstoreapi.dto;

import java.time.Instant;

public record BorrowDTO(
        Long id,
        Long bookId,
        Long clientId,
        Instant startDate,
        Instant endDate
) {
}
