package com.plands.backend.dto;

import com.plands.backend.auth.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long memberId;
    private String name;
    private String email;
    private String password;
    private Role role;           // Role enum 타입으로 수정
    private String provider;     // OAuth2 제공자명
    private String providerId;   // OAuth2 제공자에서의 유저 ID
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // MyBatis 매핑용
    private String roleString;
    public void syncRoleFromString() {
        if (this.roleString != null) {
            this.role = Role.valueOf(this.roleString);
        }
    }

    public void syncRoleToString() {
        if (this.role != null) {
            this.roleString = this.role.name();
        }
    }
}
