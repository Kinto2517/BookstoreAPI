package com.kinto2517.bookstoreapi.mapper;

import com.kinto2517.bookstoreapi.dto.ClientSaveRequest;
import com.kinto2517.bookstoreapi.entity.Client;
import com.kinto2517.bookstoreapi.dto.ClientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDTO clientToClientDTO(Client client);
    Client clientSaveRequestToClient(ClientSaveRequest clientSaveRequest);
    List<ClientDTO> clientsToClientDTOs(List<Client> clients);

}
