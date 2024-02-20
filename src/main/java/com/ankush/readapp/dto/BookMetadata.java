package com.ankush.readapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BookMetadata {

    private String author;

    private String title;

    private String language;

    private String genre;

}
