package com.ankush.readapp.handler;

import com.ankush.readapp.constants.Constants;
import com.ankush.readapp.dto.BookMetadata;
import com.ankush.readapp.mapper.BookMapper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component(Constants.FileHandler.pdfHandler)
public class PdfHandler implements FileHandler {
    @Override
    public BookMetadata extractMetadata(MultipartFile file) throws IOException {
        try (var document = PDDocument.load(file.getBytes())) {
            var info = document.getDocumentInformation();
            return BookMapper.INSTANCE.fromPDFInfo(info);
        }
    }
}
