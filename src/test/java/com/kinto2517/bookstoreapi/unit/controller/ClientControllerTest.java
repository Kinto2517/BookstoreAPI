package com.kinto2517.bookstoreapi.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinto2517.bookstoreapi.controller.ClientController;
import com.kinto2517.bookstoreapi.dto.ClientDTO;
import com.kinto2517.bookstoreapi.dto.ClientSaveRequest;
import com.kinto2517.bookstoreapi.request.PasswordChangeRequest;
import com.kinto2517.bookstoreapi.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MockMvc mockMvc;

    public ClientControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() throws Exception {
        when(clientService.getAllClients()).thenReturn(Arrays.asList(
                new ClientDTO(1L, "John", "Doe", "email123", "5445445454", "username", "1234"),
                new ClientDTO(2L, "Jane", "Doe", "email123", "5445445454", "username", "1234")
        ));

        mockMvc.perform(get("/api/v1/clients/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));

        verify(clientService, times(1)).getAllClients();
    }


}

