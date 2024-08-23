package com.ankush.readapp.mapper;

import com.ankush.readapp.dto.BookMetadata;
import com.ankush.readapp.dto.BookUploadResponse;
import com.ankush.readapp.entity.Book;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookUploadResponse toDto(Book book);

    @Mapping(target = "createdDate", expression = "java(new java.util.Date())")
    Book toEntity(String id, String fileName, BookMetadata bookMetadata);

    @Mapping(target = "genre", ignore = true)
    BookMetadata fromPDFInfo(PDDocumentInformation pdfDocInfo);
}
