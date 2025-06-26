package com.plands.backend.service;

import com.plands.backend.dto.MemberDto;
import com.plands.backend.dto.request.admin.member.MemberSearchRequestDto;
import com.plands.backend.dto.response.MemberProfileResponseDto;
import com.plands.backend.dto.response.MemberUpdateRequestDto;
import com.plands.backend.dto.response.admin.member.MemberDetailResponseDto;
import com.plands.backend.dto.response.admin.member.MemberResponseDto;
import com.plands.backend.dto.response.admin.member.MemberStatResponseDto;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    MemberDto saveMember(MemberDto memberDto);
    MemberDto saveOAuthMember(MemberDto memberDto);
    Boolean matchesPassword(String rawPassword, String encodedPassword);
    void updateMember(Long memberId, MemberUpdateRequestDto requestDto);
    MemberProfileResponseDto getMemberProfile(Long memberId);
    MemberProfileResponseDto getMemberId(Long memberId);
    MemberDto findByMemberName(String username);
    MemberDto findByProviderAndProviderId(String provider, String providerId);
    Optional<MemberDto> findByEmail(String email);

    MemberStatResponseDto getMemberStat();
    int getMemberCount(MemberSearchRequestDto requestDto);
    List<MemberResponseDto> getMemberList(MemberSearchRequestDto requestDto);
    MemberDetailResponseDto getMemberDetail(Long memberId);
    void updateMemberStatus(Long memberId, String status);
}
