package com.plands.backend.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoticeListResponseDto {
    private Long noticeId;
    private String title;
    private String keyword;
    private String createdAt;
    private String position;
}
