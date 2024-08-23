package com.ankush.readapp.handler;

import com.ankush.readapp.dto.BookMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileHandler {

    BookMetadata extractMetadata(MultipartFile file) throws IOException;

}
