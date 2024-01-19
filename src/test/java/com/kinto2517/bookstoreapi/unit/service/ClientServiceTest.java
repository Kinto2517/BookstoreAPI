package com.kinto2517.bookstoreapi.unit.service;


import com.kinto2517.bookstoreapi.dto.ClientDTO;
import com.kinto2517.bookstoreapi.dto.ClientSaveRequest;
import com.kinto2517.bookstoreapi.entity.Client;
import com.kinto2517.bookstoreapi.mapper.ClientMapper;
import com.kinto2517.bookstoreapi.repository.ClientRepository;
import com.kinto2517.bookstoreapi.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllClients_shouldReturnClientDTOList() {
        List<Client> clients = Arrays.asList(
                new Client(1L, "John", "Doe", "5442342343", null),
                new Client(2L, "Jane", "Doe", "5442342343", null)
        );
        when(clientRepository.findAll()).thenReturn(clients);

        List<ClientDTO> clientDTOS = clientService.getAllClients();

        assertEquals(2, clientDTOS.size());
        assertEquals("John", clientDTOS.get(0).firstName());
        assertEquals("Jane", clientDTOS.get(1).firstName());
    }

    @Test
    void saveClient_shouldReturnSavedClientDTO() {
        ClientSaveRequest clientSaveRequest = new ClientSaveRequest("John", "Doe",
                "email", "5442342343", "username", "password");

        Client clientToSave = ClientMapper.INSTANCE.clientSaveRequestToClient(clientSaveRequest);
        when(clientRepository.save(any(Client.class))).thenReturn(clientToSave);

        ClientDTO savedUserDTO = clientService.saveClient(clientSaveRequest);

        assertEquals("John", savedUserDTO.firstName());
        assertEquals("username", savedUserDTO.username());
    }
}

