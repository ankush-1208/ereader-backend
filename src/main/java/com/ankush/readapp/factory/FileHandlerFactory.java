package com.ankush.readapp.factory;

import com.ankush.readapp.constants.Constants;
import com.ankush.readapp.enums.FileType;
import com.ankush.readapp.handler.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FileHandlerFactory {

    private Map<String, FileHandler> fileHandlers;

    @Autowired
    public FileHandlerFactory(Map<String, FileHandler> fileHandlers) {
        this.fileHandlers = fileHandlers;
    }

    public FileHandler getFileHandler(FileType fileType) {
        return switch (fileType) {
            case PDF -> fileHandlers.get(Constants.FileHandler.pdfHandler);
            case EPUB -> fileHandlers.get(Constants.FileHandler.epubHandler);
        };
    }
}
