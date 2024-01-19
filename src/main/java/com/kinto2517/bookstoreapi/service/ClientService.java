package com.kinto2517.bookstoreapi.service;

import com.kinto2517.bookstoreapi.dto.*;
import com.kinto2517.bookstoreapi.request.PasswordChangeRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ClientService {
    List<ClientDTO> getAllClients();

    ClientDTO getClientById(Long id);

    ClientDTO saveClient(ClientSaveRequest clientSaveRequest);

    ClientDTO updateClient(Long id, ClientMainSaveRequest clientMainSaveRequest);

    void deleteClient(Long id);

    void changePassword(PasswordChangeRequest changePasswordRequest, Principal principal);
}
