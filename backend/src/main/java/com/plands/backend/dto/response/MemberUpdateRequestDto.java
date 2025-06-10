package com.plands.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequestDto {
    private String nickname;
    private String password;
    private String profileImageUrl;

}
