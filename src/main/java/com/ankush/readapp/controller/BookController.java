package com.ankush.readapp.controller;

import com.ankush.readapp.annotations.FileUploadEndpoint;
import com.ankush.readapp.dto.BookUploadResponse;
import com.ankush.readapp.dto.UserDetails;
import com.ankush.readapp.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @FileUploadEndpoint
    @PostMapping("/upload")
    public ResponseEntity<BookUploadResponse> uploadBook(MultipartFile file,
                                                         @RequestHeader("userDetails") UserDetails userDetails) {
        log.info("Received request to upload book");
        var response = bookService.processUpload(file, "", userDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> fetchBookUrl(@PathVariable("id") String id,
                                               @RequestHeader("userDetails") UserDetails userDetails) {
        log.info("Received request to fetch book having id: {}", id);
        var response = bookService.fetchBookUrl(id, userDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}