package com.kinto2517.bookstoreapi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinto2517.bookstoreapi.dto.ClientSaveRequest;
import com.kinto2517.bookstoreapi.entity.Client;
import com.kinto2517.bookstoreapi.enums.Role;
import com.kinto2517.bookstoreapi.repository.ClientRepository;
import com.kinto2517.bookstoreapi.request.AuthenticationRequest;
import com.kinto2517.bookstoreapi.response.AuthenticationResponse;
import com.kinto2517.bookstoreapi.security.JwtService;
import com.kinto2517.bookstoreapi.service.AuthenticationService;
import com.kinto2517.bookstoreapi.token.Token;
import com.kinto2517.bookstoreapi.token.TokenRepository;
import com.kinto2517.bookstoreapi.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {


    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    @Transactional
    public AuthenticationResponse clientRegister(ClientSaveRequest request) {

        var client = Client.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.CLIENT)
                .build();
        if (clientRepository.existsByUsername(client.getUsername())) {
            return AuthenticationResponse.builder()
                    .error("Username already exists")
                    .build();
        }
        if (clientRepository.existsByEmail(client.getEmail())) {
            return AuthenticationResponse.builder()
                    .error("Email already exists")
                    .build();
        }
        var savedClient = clientRepository.save(client);
        var jwtToken = jwtService.generateToken(client);
        var refreshToken = jwtService.generateRefreshToken(client);
        saveClientToken(savedClient, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .error(null)
                .build();
    }


    public AuthenticationResponse authenticateClient(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        if (!clientRepository.existsByUsername(request.getUsername())) {
            return AuthenticationResponse.builder()
                    .error("Username does not exist")
                    .build();
        }
        var client = clientRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(client);
        var refreshToken = jwtService.generateRefreshToken(client);
        revokeAllClientTokens(client);
        saveClientToken(client, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .error(null)
                .build();
    }


    @Transactional
    public void saveClientToken(Client client, String jwtToken) {
        var token = Token.builder()
                .user(client)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }


    private void revokeAllClientTokens(Client client) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(client.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


    public void refreshClientToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var client = this.clientRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, client)) {
                var accessToken = jwtService.generateToken(client);
                revokeAllClientTokens(client);
                saveClientToken(client, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
