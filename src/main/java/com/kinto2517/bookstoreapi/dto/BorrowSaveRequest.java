package com.kinto2517.bookstoreapi.dto;

import java.time.Instant;

public record BorrowSaveRequest(
        Long bookId,
        Long clientId,
        Instant startDate,
        Instant endDate
) {
}
