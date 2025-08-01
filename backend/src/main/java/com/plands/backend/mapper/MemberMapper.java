package com.plands.backend.mapper;

import com.plands.backend.dto.MemberDto;
import com.plands.backend.dto.response.MemberProfileResponseDto;
import com.plands.backend.dto.response.MemberUpdateRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    void insertMember(MemberDto member);
    int updateMember(@Param("memberId") Long memberId,
                     @Param("dto") MemberUpdateRequestDto requestDto);
    MemberProfileResponseDto getMemberProfile(@Param("memberId") Long memberId);
    MemberProfileResponseDto getMemberId(Long memberId);
    MemberDto findByMemberName(String name);
    MemberDto findByEmail(String email);
    MemberDto findByProviderAndProviderId(@Param("provider") String provider, @Param("providerId") String providerId);
    int updateProfileImage(@Param("memberId") Long memberId, @Param("profileImageUrl") String profileImageUrl);
}
