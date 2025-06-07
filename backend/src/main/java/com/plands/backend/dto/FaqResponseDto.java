package com.plands.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaqResponseDto {
    private Long faqId;
    private String question;
    private String answer;
    private String keyword;
    private int page = 1;
    private int size = 8;
}
