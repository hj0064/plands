package com.plands.backend.dto.response.admin.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberListResponseDto {
    private List<MemberResponseDto> members;
    private int totalCount;
}
