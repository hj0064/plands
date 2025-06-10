package com.plands.backend.mapper;

import com.plands.backend.dto.request.NoticeSearchRequestDto;
import com.plands.backend.dto.response.NoticeDetailResponseDto;
import com.plands.backend.dto.response.NoticeListResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {
     List<NoticeListResponseDto> selectNoticeList(@Param("search") NoticeSearchRequestDto search);
     int selectNoticeCount(@Param("search") NoticeSearchRequestDto search);
     NoticeDetailResponseDto selectNoticeDetail(@Param("noticeId") Long noticeId);
     List<NoticeListResponseDto> selectAdjacentNotices(Long noticeId);
}
