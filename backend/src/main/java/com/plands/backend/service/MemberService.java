package com.plands.backend.service;

import com.plands.backend.dto.MemberDto;

import java.util.Optional;

public interface MemberService {
    MemberDto saveMember(MemberDto memberDto);
    MemberDto saveOAuthMember(MemberDto memberDto);
    MemberDto findByMemberName(String username);
    Optional<MemberDto> findByEmail(String email);
    Boolean matchesPassword(String rawPassword, String encodedPassword);
    MemberDto findByProviderAndProviderId(String provider, String providerId);
}
