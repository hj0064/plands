package com.plands.backend.controller;

import com.plands.backend.common.dto.PageResult;
import com.plands.backend.dto.response.FaqResponseDto;
import com.plands.backend.service.FaqServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/faq")
public class FaqController {

    private final FaqServiceImpl faqService;

    public FaqController(FaqServiceImpl faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public ResponseEntity<PageResult<FaqResponseDto>> getFaqs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(required = false) String keyword) {

        PageResult<FaqResponseDto> result = faqService.getFaqs(page, size, keyword);
        return ResponseEntity.ok(result);
    }
}
