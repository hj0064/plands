package com.plands.backend.service;

import com.plands.backend.dto.request.NoticeSearchRequestDto;
import com.plands.backend.dto.response.NoticeDetailResponseDto;
import com.plands.backend.dto.response.NoticeListResponseDto;
import com.plands.backend.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    public NoticeServiceImpl(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    @Override
    public List<NoticeListResponseDto> getNoticeList(NoticeSearchRequestDto noticeSearchRequestDto) {
        return noticeMapper.selectNoticeList(noticeSearchRequestDto);
    }

    @Override
    public int getNoticeCount(NoticeSearchRequestDto noticeSearchRequestDto) {
        return noticeMapper.selectNoticeCount(noticeSearchRequestDto);
    }

    @Override
    public NoticeDetailResponseDto getNoticeDetail(Long noticeId) {
        return noticeMapper.selectNoticeDetail(noticeId);
    }

    @Override
    public Map<String, NoticeListResponseDto> getAdjacentNotices(Long noticeId) {
        List<NoticeListResponseDto> list = noticeMapper.selectAdjacentNotices(noticeId);

        Map<String, NoticeListResponseDto> result = new HashMap<>();
        for (NoticeListResponseDto item : list) {
            result.put(item.getPosition(), item); // position: "prev" 또는 "next"
        }

        return result;
    }
}
