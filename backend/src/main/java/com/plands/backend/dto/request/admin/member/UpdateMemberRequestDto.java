package com.plands.backend.dto.request.admin.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMemberRequestDto {
    private Long memberId;
    private String status;
}
