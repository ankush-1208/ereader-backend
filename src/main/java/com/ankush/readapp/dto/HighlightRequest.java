package com.ankush.readapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class HighlightRequest {

    private String bookId;

    private String bookName;

    private String highlightText;

    private Integer pageNumber;

    private String notes;

    private List<String> tags;

    private String color;

}
