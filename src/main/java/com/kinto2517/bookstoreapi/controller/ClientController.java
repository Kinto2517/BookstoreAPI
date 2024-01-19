package com.kinto2517.bookstoreapi.controller;

import com.kinto2517.bookstoreapi.dto.ClientDTO;
import com.kinto2517.bookstoreapi.dto.ClientMainSaveRequest;
import com.kinto2517.bookstoreapi.entity.Client;
import com.kinto2517.bookstoreapi.request.PasswordChangeRequest;
import com.kinto2517.bookstoreapi.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    public ClientController(ClientService clientService ) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id,
                                                  @RequestBody ClientMainSaveRequest clientMainSaveRequest,
                                                  Authentication authentication) {

        Client authenticatedClient = (Client) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedClient.getUsername());

        if (!authenticatedClient.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(clientService.updateClient(id, clientMainSaveRequest));
    }

    @PatchMapping("/change-password")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeRequest changePasswordRequest,
                                               Principal principal) {
        clientService.changePassword(changePasswordRequest, principal);
        return ResponseEntity.ok().build();
    }

}
