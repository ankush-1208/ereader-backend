package com.ankush.readapp.handler;

import com.ankush.readapp.constants.Constants;
import com.ankush.readapp.dto.BookMetadata;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component(Constants.FileHandler.pdfHandler)
public class PdfHandler implements FileHandler {
    @Override
    public BookMetadata extractMetadata(MultipartFile file) {
        return null;
    }
}
