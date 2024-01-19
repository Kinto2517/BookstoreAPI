package com.kinto2517.bookstoreapi.dto;

public record BookstoreSaveRequest(
        String name,
        String address,
        String phoneNumber
) {
}
