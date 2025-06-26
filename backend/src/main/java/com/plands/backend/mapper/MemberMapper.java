package com.plands.backend.mapper;

import com.plands.backend.dto.MemberDto;
import com.plands.backend.dto.request.admin.member.MemberSearchRequestDto;
import com.plands.backend.dto.response.MemberProfileResponseDto;
import com.plands.backend.dto.response.MemberUpdateRequestDto;
import com.plands.backend.dto.response.admin.member.MemberDetailResponseDto;
import com.plands.backend.dto.response.admin.member.MemberResponseDto;
import com.plands.backend.dto.response.admin.member.MemberStatResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    // cilent
    // 회원가입, 로그인, 내 정보 수정, 프로필 수정
    void insertMember(MemberDto member);
    int updateMember(@Param("memberId") Long memberId,
                     @Param("dto") MemberUpdateRequestDto requestDto);
    MemberProfileResponseDto getMemberProfile(@Param("memberId") Long memberId);
    MemberProfileResponseDto getMemberId(Long memberId);
    MemberDto findByMemberName(String name);
    MemberDto findByEmail(String email);
    MemberDto findByProviderAndProviderId(@Param("provider") String provider, @Param("providerId") String providerId);
    int updateProfileImage(@Param("memberId") Long memberId, @Param("profileImageUrl") String profileImageUrl);

    // admin
    // 유저 정보 조회(리스트, 상세), 유저 검색, 유저 정보 수정(유저 상태)
    MemberStatResponseDto getMemberStat();
    int getMemberCount(MemberSearchRequestDto requestDto);
    List<MemberResponseDto> getMemberList(MemberSearchRequestDto requestDto);
    MemberDetailResponseDto getMemberDetail(@Param("memberId") Long memberId);
    int updateMemberState(@Param("memberId") Long memberId, @Param("state") String state);
}
