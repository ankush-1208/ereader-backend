package com.ankush.readapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HighlightRequest {

    private String bookId;

    private String highlightText;

    private Integer pageNumber;



}
