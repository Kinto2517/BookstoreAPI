package com.kinto2517.bookstoreapi.mapper;

import com.kinto2517.bookstoreapi.dto.BookstoreDTO;
import com.kinto2517.bookstoreapi.dto.BookstoreSaveRequest;
import com.kinto2517.bookstoreapi.entity.Bookstore;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookstoreMapper {

    BookstoreMapper INSTANCE = Mappers.getMapper(BookstoreMapper.class);

    BookstoreDTO bookstoreToBookstoreDTO(Bookstore bookstore);

    Bookstore bookstoreSaveRequestToBookstore(BookstoreSaveRequest bookstoreSaveRequest);

    List<BookstoreDTO> bookstoresToBookstoreDTOs(List<Bookstore> bookstores);
}
