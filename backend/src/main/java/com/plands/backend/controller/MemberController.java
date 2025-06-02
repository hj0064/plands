package com.plands.backend.controller;

import com.plands.backend.dto.MemberProfileResponseDto;
import com.plands.backend.dto.MemberUpdateRequestDto;
import com.plands.backend.service.MemberServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberServiceImpl memberService;

    public MemberController(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }

    /**
     * 회원 정보를 수정합니다.
     *
     * @param memberId 수정할 회원 ID
     * @param requestDto 수정할 회원 정보가 담긴 DTO
     * @return 수정 성공 여부
     */
    @PutMapping("/{memberId}")
    public ResponseEntity<String> updateMember(
            @PathVariable Long memberId,
            @RequestBody MemberUpdateRequestDto requestDto) {
        memberService.updateMember(memberId, requestDto);
        return ResponseEntity.ok("update member success");
    }

    /**
     * 회원의 종합적인 정보를 조회합니다.
     *
     * @param memberId 조회하는 회원 ID
     * @return 조회된 회원 프로필 정보를 담은 ResponseEntity 객체
     */
    @GetMapping("/{memberId}/profile")
    public ResponseEntity<MemberProfileResponseDto> getMemberProfile(@PathVariable Long memberId) {
        MemberProfileResponseDto profile = memberService.getMemberProfile(memberId);
        return ResponseEntity.ok(profile);
    }
}