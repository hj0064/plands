package com.plands.backend.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoticeDetailResponseDto {
    private Long noticeId;
    private String title;
    private String content;
    private String keyword;
    private String createdAt;
}
