package com.kinto2517.bookstoreapi.service;


import com.kinto2517.bookstoreapi.dto.ClientSaveRequest;
import com.kinto2517.bookstoreapi.request.AuthenticationRequest;
import com.kinto2517.bookstoreapi.response.AuthenticationResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponse clientRegister(ClientSaveRequest request);

    AuthenticationResponse authenticateClient(AuthenticationRequest request);

    void refreshClientToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
