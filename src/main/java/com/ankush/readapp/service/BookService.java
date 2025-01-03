package com.ankush.readapp.service;

import com.ankush.readapp.dto.BookUploadResponse;
import com.ankush.readapp.entity.Book;
import com.ankush.readapp.enums.FileType;
import com.ankush.readapp.exception.UnauthorizedException;
import com.ankush.readapp.factory.FileHandlerFactory;
import com.ankush.readapp.helper.UserDetailsHelper;
import com.ankush.readapp.mapper.BookMapper;
import com.ankush.readapp.repository.BookRepository;
import com.ankush.readapp.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class BookService {

    @Autowired
    private S3FileServer s3FileServer;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private FileHandlerFactory fileHandlerFactory;

    /**
     * Uploads the file to server and initializes an entry in the database
     *
     * @param file The file the user uploaded
     * @param bookTitle title of the book
     * @return The id of the file uploaded
     */
    public BookUploadResponse processUpload(MultipartFile file, String bookTitle) throws IOException {
        log.info("Processing file upload");
        var bookId = Utils.generateId();
        var userDetails = UserDetailsHelper.getCurrentUserDetails();
        // Upload the file to server
        var fileName = s3FileServer.uploadFileToS3(file, bookId);
        var book = Book.builder()
                .id(Utils.generateId())
                .title(Utils.getOrDefault(bookTitle, file.getOriginalFilename()))
                .userId(userDetails.getId())
                .fileType(FileType.getFileType(file.getContentType()))
                .createdDate(Utils.getCurrentDate())
                .build();
        book.setFileName(fileName);
        var bookMetadata = fileHandlerFactory.getFileHandler(book.getFileType()).extractMetadata(file);
        BookMapper.INSTANCE.toEntity(bookId, fileName, bookMetadata);
        bookRepository.save(book);
        return BookMapper.INSTANCE.toDto(book);
    }


    public String fetchBookUrl(String id) {
        var userDetails = UserDetailsHelper.getCurrentUserDetails();
        var book = bookRepository.findByIdAndUserId(id, userDetails.getId()).orElseThrow(
                () -> new UnauthorizedException("This book does not belong to the user")
        );
        return s3FileServer.fetchFileFromS3(book.getFileName());
    }
}
