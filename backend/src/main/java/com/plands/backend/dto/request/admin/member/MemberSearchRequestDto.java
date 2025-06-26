package com.plands.backend.dto.request.admin.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSearchRequestDto {
    private String username;
    private String email;
    private String provider;
    private String status;
    private String role;
    private String startDate;
    private String endDate;
    private String sort;
    private int page = 1;
    private int size = 10;

    public int getOffset() {
        return (page - 1) * size;
    }
}
