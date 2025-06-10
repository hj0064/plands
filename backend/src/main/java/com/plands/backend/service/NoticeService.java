package com.plands.backend.service;

import com.plands.backend.dto.request.NoticeSearchRequestDto;
import com.plands.backend.dto.response.NoticeDetailResponseDto;
import com.plands.backend.dto.response.NoticeListResponseDto;

import java.util.List;
import java.util.Map;

public interface NoticeService {
    List<NoticeListResponseDto>getNoticeList(NoticeSearchRequestDto noticeSearchRequestDto);
    int getNoticeCount(NoticeSearchRequestDto noticeSearchRequestDto);
    NoticeDetailResponseDto getNoticeDetail(Long noticeId);
    Map<String, NoticeListResponseDto> getAdjacentNotices(Long noticeId);
}
