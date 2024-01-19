package com.kinto2517.bookstoreapi.dto;

import java.time.Instant;

public record BookSaveRequest(
        String title,
        String author,
        Instant publishDate,
        String bookstoreName
) {
}
