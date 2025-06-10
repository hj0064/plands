package com.plands.backend.controller;

import com.plands.backend.dto.request.NoticeSearchRequestDto;
import com.plands.backend.dto.response.NoticeDetailResponseDto;
import com.plands.backend.dto.response.NoticeListResponseDto;
import com.plands.backend.service.NoticeServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    private final NoticeServiceImpl noticeService;

    public NoticeController(NoticeServiceImpl noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public Map<String, Object> getNoticeList(NoticeSearchRequestDto searchDto) {
        List<NoticeListResponseDto> notices = noticeService.getNoticeList(searchDto);
        int totalCount = noticeService.getNoticeCount(searchDto);
        Map<String, Object> response = new HashMap<>();
        response.put("notices", notices);
        response.put("totalCount", totalCount);
        response.put("page", searchDto.getPage());
        response.put("size", searchDto.getSize());
        return response;
    }

    @GetMapping("/{noticeId}")
    public NoticeDetailResponseDto getNoticeDetail(@PathVariable Long noticeId) {
        return noticeService.getNoticeDetail(noticeId);
    }

    @GetMapping("/{noticeId}/adjacent")
    public Map<String, NoticeListResponseDto> getAdjacentNotices(@PathVariable Long noticeId) {
        return noticeService.getAdjacentNotices(noticeId);
    }
}
