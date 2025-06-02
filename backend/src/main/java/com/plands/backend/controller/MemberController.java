package com.plands.backend.controller;

import com.plands.backend.dto.MemberProfileResponseDto;
import com.plands.backend.dto.MemberUpdateRequestDto;
import com.plands.backend.mapper.MemberMapper;
import com.plands.backend.service.FileStorageService;
import com.plands.backend.service.MemberServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberServiceImpl memberService;
    private final FileStorageService fileStorageService;
    private final MemberMapper memberMapper;

    public MemberController(MemberServiceImpl memberService, FileStorageService fileStorageService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.fileStorageService = fileStorageService;
        this.memberMapper = memberMapper;
    }

    /**
     * 회원의 정보 및 활동을 조회합니다.
     *
     * @param memberId 조회하는 회원 ID
     * @return 조회된 회원 프로필 정보를 담은 ResponseEntity 객체
     */
    @GetMapping("/{memberId}/profile")
    public ResponseEntity<MemberProfileResponseDto> getMemberProfile(@PathVariable Long memberId) {
        MemberProfileResponseDto profile = memberService.getMemberProfile(memberId);
        return ResponseEntity.ok(profile);
    }

    /**
     * 회원의 종합적인 정보를 조회합니다.
     *
     * @return 조회된 회원 프로필 정보를 담은 ResponseEntity 객체
     */
    @GetMapping("/me/{memberId}")
    public ResponseEntity<MemberProfileResponseDto> getMemberId(@PathVariable Long memberId) {
        MemberProfileResponseDto profile = memberService.getMemberId(memberId);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/update/{memberId}")
    public ResponseEntity<Void> updateMember(
            @PathVariable Long memberId,
            @RequestBody MemberUpdateRequestDto request) {
        memberService.updateMember(memberId, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/me/profile-image")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("profileImage") MultipartFile file,
                                                @RequestParam("memberId") Long memberId) throws IOException {

        String savedPath = fileStorageService.saveProfileImage(file);

        MemberProfileResponseDto member = memberService.getMemberId(memberId);
        if (member == null) {
            throw new RuntimeException("회원 없음");
        }
        member.setProfileImageUrl(savedPath);
        memberMapper.updateProfileImage(member.getMemberId(), member.getProfileImageUrl());

        return ResponseEntity.ok(Map.of("profileImageUrl", savedPath));
    }
}