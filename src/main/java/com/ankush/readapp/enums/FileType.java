package com.ankush.readapp.enums;

import com.ankush.readapp.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FileType {
    PDF("application/pdf"), EPUB("application/epub+zip");

    public String contentType;

    FileType(String contentType) {
        this.contentType = contentType;
    }

    public static FileType getFileType(String contentType) {
        return Arrays.stream(FileType.values()).filter(type -> type.getContentType().equalsIgnoreCase(contentType)).findFirst().orElseThrow(
                () -> new BadRequestException("File Type not supported")
        );
    }
}
