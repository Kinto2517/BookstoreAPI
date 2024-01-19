package com.kinto2517.bookstoreapi.controller;

import com.kinto2517.bookstoreapi.dto.ClientSaveRequest;
import com.kinto2517.bookstoreapi.request.AuthenticationRequest;
import com.kinto2517.bookstoreapi.response.AuthenticationResponse;
import com.kinto2517.bookstoreapi.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/clientregister")
    public ResponseEntity<AuthenticationResponse> clientRegister(@RequestBody ClientSaveRequest request){
        AuthenticationResponse response = authenticationService.clientRegister(request);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/clientauthenticate")
    public ResponseEntity<AuthenticationResponse> clientAuthenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticateClient(request));
    }

    @PostMapping("/refresh-client-token")
    public void refreshClientToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshClientToken(request, response);
    }


}
