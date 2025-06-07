package com.plands.backend.mapper;

import com.plands.backend.dto.FaqResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FaqMapper {
     List<FaqResponseDto> selectFaqs(@Param("offset") int offset, @Param("size") int size, @Param("keyword") String keyword);
     int countFaqs(@Param("keyword") String keyword);
}
