package com.kinto2517.bookstoreapi.mapper;

import com.kinto2517.bookstoreapi.dto.BorrowDTO;
import com.kinto2517.bookstoreapi.dto.BorrowSaveRequest;
import com.kinto2517.bookstoreapi.entity.Book;
import com.kinto2517.bookstoreapi.entity.Borrow;
import com.kinto2517.bookstoreapi.entity.Client;
import com.kinto2517.bookstoreapi.repository.BookRepository;
import com.kinto2517.bookstoreapi.repository.ClientRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BorrowMapper {

    BorrowMapper INSTANCE = Mappers.getMapper(BorrowMapper.class);

    @Mappings({
            @Mapping(source = "book.id", target = "bookId"),
            @Mapping(source = "client.id", target = "clientId")
    })
    BorrowDTO borrowToBorrowDTO(Borrow borrow);

    Borrow borrowSaveRequestToBorrow(BorrowSaveRequest borrowSaveRequest);

    List<BorrowDTO> borrowsToBorrowDTOs(List<Borrow> borrows);
}
