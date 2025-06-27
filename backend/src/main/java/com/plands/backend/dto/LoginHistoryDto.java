package com.plands.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginHistoryDto {
    private Long memberId;
    private String ipAddress;
    private String userAgent;
}
