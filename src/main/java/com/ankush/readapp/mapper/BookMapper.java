package com.ankush.readapp.mapper;

import com.ankush.readapp.dto.BookUploadResponse;
import com.ankush.readapp.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookUploadResponse toDto(Book book);
}
