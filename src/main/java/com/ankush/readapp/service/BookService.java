package com.ankush.readapp.service;

import com.ankush.readapp.dto.BookUploadResponse;
import com.ankush.readapp.dto.UserDetails;
import com.ankush.readapp.entity.Book;
import com.ankush.readapp.exception.UnauthorizedException;
import com.ankush.readapp.mapper.BookMapper;
import com.ankush.readapp.repository.BookRepository;
import com.ankush.readapp.utils.Utils;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class BookService {

    @Autowired
    private S3FileServer s3FileServer;

    @Autowired
    private BookRepository bookRepository;

    /**
     * Uploads the file to server and initializes an entry in the database
     *
     * @param file The file the user uploaded
     * @param bookTitle title of the book
     * @param userDetails user info
     * @return The id of the file uploaded
     */
    public BookUploadResponse processUpload(MultipartFile file, String bookTitle, UserDetails userDetails) {
        log.info("Processing file upload");
        var book = Book.builder()
                .id(Utils.generateId())
                .name(Utils.getOrDefault(bookTitle, file.getOriginalFilename()))
                .userId(userDetails.getUserId())
                .uploadDate(Utils.getCurrentDate())
                .build();
        // Upload the file to server
        var fileName = s3FileServer.uploadFileToS3(file, book.getId());
        book.setFileName(fileName);
        bookRepository.save(book);
        return BookMapper.INSTANCE.toDto(book);
    }

    public String fetchBookUrl(String id, UserDetails userDetails) {
        var book = bookRepository.findByIdAndUserId(id, userDetails.getUserId()).orElseThrow(
                () -> new UnauthorizedException("This book does not belong to the user")
        );
        return s3FileServer.fetchFileFromS3(book.getFileName());
    }
}
