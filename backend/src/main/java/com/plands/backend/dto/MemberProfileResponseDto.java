package com.plands.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfileResponseDto {
    private String nickname;
    private String profileImageUrl;
    private int postCount;
    private int commentCount;
    private int plantCount;
    private int diaryCount;
}
