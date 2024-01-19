package com.kinto2517.bookstoreapi.dto;

import java.time.Instant;

public record BorrowUpdateRequest(
        Long clientId,
        Instant startDate,
        Instant endDate
) {

}
