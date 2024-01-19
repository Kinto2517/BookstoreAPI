package com.kinto2517.bookstoreapi.mapper;

import com.kinto2517.bookstoreapi.dto.BookDTO;
import com.kinto2517.bookstoreapi.dto.BookSaveRequest;
import com.kinto2517.bookstoreapi.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "bookstoreName", source = "bookstore.name")
    BookDTO bookToBookDTO(Book book);

    Book bookSaveRequestToBook(BookSaveRequest bookSaveRequest);

    List<BookDTO> booksToBookDTOs(List<Book> books);
}
