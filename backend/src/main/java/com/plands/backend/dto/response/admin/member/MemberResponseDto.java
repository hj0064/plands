package com.plands.backend.dto.response.admin.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto {
    private Long memberId;
    private String name;
    private String email;
    private String nickname;
    private String role;
    private String provider;
    private String status;
    private String createdAt;
}
