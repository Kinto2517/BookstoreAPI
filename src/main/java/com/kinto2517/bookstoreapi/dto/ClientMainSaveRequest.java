package com.kinto2517.bookstoreapi.dto;

public record ClientMainSaveRequest(
        String firstName,
        String lastName,
        String phoneNumber
) {
}
