package com.plands.backend.service;

import com.plands.backend.dto.MemberDto;
import com.plands.backend.dto.response.MemberProfileResponseDto;
import com.plands.backend.dto.response.MemberUpdateRequestDto;

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

}
