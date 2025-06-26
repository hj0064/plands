package com.plands.backend.dto.response.admin.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetailResponseDto {
    private Long memberId;
    private String name;
    private String email;
    private String nickname;
    private String provider;
    private String role;
    private String status;
    private String createAt;
    private String updateAt;
    private String profileImageUrl;
}
