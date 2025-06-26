package com.plands.backend.service;

import com.plands.backend.auth.Role;
import com.plands.backend.dto.MemberDto;
import com.plands.backend.dto.request.admin.member.MemberSearchRequestDto;
import com.plands.backend.dto.request.admin.member.UpdateMemberRequestDto;
import com.plands.backend.dto.response.MemberProfileResponseDto;
import com.plands.backend.dto.response.MemberUpdateRequestDto;
import com.plands.backend.dto.response.admin.member.MemberDetailResponseDto;
import com.plands.backend.dto.response.admin.member.MemberResponseDto;
import com.plands.backend.dto.response.admin.member.MemberStatResponseDto;
import com.plands.backend.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 회원 정보를 관리하는 서비스 구현 클래스
 * 비즈니스 로직을 처리합니다.
 *
 * @author HJ IM
 * @version 1.0
 * @since 2025-05-29
 */
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
    public Boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequestDto requestDto) {
        // 비밀번호가 비어 있지 않은 경우 암호화
        if (requestDto.getPassword() != null && !requestDto.getPassword().isBlank()) {
            String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
            requestDto.setPassword(encodedPassword);
        } else {
            // 비밀번호가 비어 있다면 null로 설정해서 MyBatis에서 UPDATE 대상에서 제외되도록 유도
            requestDto.setPassword(null);
        }

        int updatedCount = memberMapper.updateMember(memberId, requestDto);
        if (updatedCount == 0) {
            throw new RuntimeException("회원 정보 수정에 실패했습니다. 존재하지 않는 회원입니다.");
        }
    }

    @Override
    public MemberProfileResponseDto getMemberProfile(Long memberId) {
        return memberMapper.getMemberProfile(memberId);
    }

    @Override
    public MemberProfileResponseDto getMemberId(Long memberId) {
        return memberMapper.getMemberId(memberId);
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
    public MemberStatResponseDto getMemberStat() {
        return memberMapper.getMemberStat();
    }

    @Override
    public int getMemberCount(MemberSearchRequestDto requestDto) {
        return memberMapper.getMemberCount(requestDto);
    }

    @Override
    public List<MemberResponseDto> getMemberList(MemberSearchRequestDto requestDto) {
        return memberMapper.getMemberList(requestDto);
    }

    @Override
    public MemberDetailResponseDto getMemberDetail(Long memberId) {
        return memberMapper.getMemberDetail(memberId);
    }

    @Override
    @Transactional
    public void updateMemberStatus(Long memberId, String status) {
        UpdateMemberRequestDto requestDto = new UpdateMemberRequestDto();
        requestDto.setStatus(status);
        requestDto.setMemberId(memberId);
        int updatedCount = memberMapper.updateMemberState(memberId, status);
        if (updatedCount == 0) {
            throw new RuntimeException("회원 정보 수정에 실패했습니다. 존재하지 않는 회원입니다.");
        }
    }


    @Override
    public MemberDto findByProviderAndProviderId(String provider, String providerId) {
        return memberMapper.findByProviderAndProviderId(provider, providerId);
    }
}
