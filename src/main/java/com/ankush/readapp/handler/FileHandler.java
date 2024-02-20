package com.ankush.readapp.handler;

import com.ankush.readapp.dto.BookMetadata;
import org.springframework.web.multipart.MultipartFile;

public interface FileHandler {

    BookMetadata extractMetadata(MultipartFile file);

}
