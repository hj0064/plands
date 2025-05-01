package com.plands.backend.mapper;

import com.plands.backend.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    MemberDto findByMemberName(String name);
    MemberDto findByEmail(String email);
    MemberDto findByProviderAndProviderId(@Param("provider") String provider, @Param("providerId") String providerId);
    void insertMember(MemberDto member);
}
