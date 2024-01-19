package com.kinto2517.bookstoreapi.dto;

public record BookstoreDTO(
        Long id,
        String name,
        String address,
        String phoneNumber
) {
}
