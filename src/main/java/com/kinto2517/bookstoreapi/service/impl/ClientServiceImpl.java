package com.kinto2517.bookstoreapi.service.impl;

import com.kinto2517.bookstoreapi.dto.*;
import com.kinto2517.bookstoreapi.entity.Client;
import com.kinto2517.bookstoreapi.mapper.ClientMapper;
import com.kinto2517.bookstoreapi.repository.ClientRepository;
import com.kinto2517.bookstoreapi.request.PasswordChangeRequest;
import com.kinto2517.bookstoreapi.service.ClientService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository,
                             PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return ClientMapper.INSTANCE.clientsToClientDTOs(clients);
    }

    @Override
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow();
        return ClientMapper.INSTANCE.clientToClientDTO(client);
    }

    @Override
    public ClientDTO saveClient(ClientSaveRequest clientSaveRequest) {
        Client client = ClientMapper.INSTANCE.clientSaveRequestToClient(clientSaveRequest);
        Client savedClient = clientRepository.save(client);
        return ClientMapper.INSTANCE.clientToClientDTO(savedClient);
    }

    @Override
    public ClientDTO updateClient(Long id, ClientMainSaveRequest clientMainSaveRequest) {
        Client client = clientRepository.findById(id).orElseThrow();
        client.setFirstName(clientMainSaveRequest.firstName());
        client.setLastName(clientMainSaveRequest.lastName());
        client.setPhoneNumber(clientMainSaveRequest.phoneNumber());

        Client savedClient = clientRepository.save(client);
        return ClientMapper.INSTANCE.clientToClientDTO(savedClient);
    }

    @Override
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client with id " + id + " does not exist");
        }
        clientRepository.deleteById(id);
    }

    @Override
    public void changePassword(PasswordChangeRequest changePasswordRequest, Principal principal) {
        Client client = (Client) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), client.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getNewPasswordConfirmation())) {
            throw new RuntimeException("New password and confirm password do not match");
        }

        client.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        clientRepository.save(client);
    }


}
