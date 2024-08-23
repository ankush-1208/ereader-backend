package com.ankush.readapp.service;

import com.ankush.readapp.dto.HighlightRequest;
import com.ankush.readapp.helper.UserDetailsHelper;
import com.ankush.readapp.mapper.HighlightMapper;
import com.ankush.readapp.repository.HighlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HighlightService {

    private final HighlightRepository highlightRepository;

    public void addHighlight(HighlightRequest highlightRequest) {
        log.info("Received request to add highlight for book: {}", highlightRequest.getBookId());
        var userDetails = UserDetailsHelper.getCurrentUserDetails();
        var highlight = HighlightMapper.INSTANCE.toEntity(highlightRequest, userDetails);
        highlightRepository.save(highlight);
    }
}
