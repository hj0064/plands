package com.plands.backend.dto.response.admin.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberStatResponseDto {
    private int totalUsers;
    private int activeUsers;
    private int newUsers;
    private int totalPlants;
}
