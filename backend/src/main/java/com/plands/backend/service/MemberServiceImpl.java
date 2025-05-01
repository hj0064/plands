package com.plands.backend.service;

import com.plands.backend.auth.Role;
import com.plands.backend.dto.MemberDto;
import com.plands.backend.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper, BCryptPasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public MemberDto saveMember(MemberDto memberDto) {
        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
        MemberDto member = new MemberDto();
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        member.setPassword(encodedPassword);
        member.setRole(Role.ROLE_USER);  // 기본 역할 설정
        memberMapper.insertMember(member);
        return member;
    }

    @Override
    public MemberDto saveOAuthMember(MemberDto memberDto) {
        if (memberDto.getPassword() == null) {
            memberDto.setPassword(""); // OAuth 유저는 비밀번호가 없을 수 있음
        }
        // 여기서는 role이 이미 enum이므로 변환 불필요
        memberMapper.insertMember(memberDto);
        return memberDto;
    }

    @Override
    public MemberDto findByMemberName(String username) {
        return memberMapper.findByMemberName(username);
    }

    @Override
    public Optional<MemberDto> findByEmail(String email) {
        return Optional.ofNullable(memberMapper.findByEmail(email));
    }

    @Override
    public Boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public MemberDto findByProviderAndProviderId(String provider, String providerId) {
        return memberMapper.findByProviderAndProviderId(provider, providerId);
    }
}
