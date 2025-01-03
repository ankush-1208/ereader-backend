package com.ankush.readapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class BookUploadResponse {

    private String id;

    private String bookTitle;

    private String authorName;

    private String genre;

}
