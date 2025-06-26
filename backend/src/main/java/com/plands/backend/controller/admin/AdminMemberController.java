package com.plands.backend.controller.admin;

import com.plands.backend.dto.request.admin.member.MemberSearchRequestDto;
import com.plands.backend.dto.request.admin.member.UpdateMemberRequestDto;
import com.plands.backend.dto.response.admin.member.MemberDetailResponseDto;
import com.plands.backend.dto.response.admin.member.MemberListResponseDto;
import com.plands.backend.dto.response.admin.member.MemberResponseDto;
import com.plands.backend.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/member")
public class AdminMemberController {

    private final MemberService memberService;

    public AdminMemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<MemberListResponseDto> getMembers(MemberSearchRequestDto memberSearchRequestDto) {
        List<MemberResponseDto> members = memberService.getMemberList(memberSearchRequestDto);
        int totalCount = memberService.getMemberCount(memberSearchRequestDto);
        MemberListResponseDto requset = new MemberListResponseDto();
        requset.setTotalCount(totalCount);
        requset.setMembers(members);
        return ResponseEntity.ok(requset);
    }

    @GetMapping("/stat")
    public ResponseEntity<?> getMemberStat() {
        return ResponseEntity.ok(memberService.getMemberStat());
    }

    @GetMapping("/{memberId}/profile")
    public ResponseEntity<MemberDetailResponseDto> getMemberDetail(@PathVariable("memberId") Long memberId) {
        MemberDetailResponseDto memberDetail = memberService.getMemberDetail(memberId);
        return ResponseEntity.ok(memberDetail);
    }

    @PatchMapping("/{memberId}/status")
    public ResponseEntity<Void> updateMemberStatus(@PathVariable("memberId") Long memberId, @RequestBody UpdateMemberRequestDto requestDto) {
        memberService.updateMemberStatus(memberId, requestDto.getStatus());
        return ResponseEntity.ok().build();
    }
}
