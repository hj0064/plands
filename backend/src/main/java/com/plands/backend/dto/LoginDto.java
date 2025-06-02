package com.plands.backend.dto;

import com.plands.backend.auth.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private JwtToken jwtToken;
    private Long memberId;
    private String nickname;
    private String profileImageUrl;
}
