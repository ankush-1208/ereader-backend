package com.ankush.readapp.controller;

import com.ankush.readapp.dto.HighlightRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("")
public class HighlightsController {


    @PostMapping("/highlight")
    public ResponseEntity<?> addHighlight(@RequestBody HighlightRequest highlightRequest) {
        log.info("Received request to add highlights for bookId: {}", highlightRequest);
        return null;
    }

}
