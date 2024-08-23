package com.ankush.readapp.controller;

import com.ankush.readapp.dto.HighlightRequest;
import com.ankush.readapp.service.HighlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class HighlightController {

    private final HighlightService highlightService;

    @PostMapping("/highlight")
    public ResponseEntity<Void> addHighlight(@RequestBody HighlightRequest highlightRequest) {
        log.info("Received request to add highlights for bookId: {}", highlightRequest);
        highlightService.addHighlight(highlightRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
