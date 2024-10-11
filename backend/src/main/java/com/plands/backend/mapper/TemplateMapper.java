package com.plands.backend.mapper;

import com.plands.backend.dto.TemplateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TemplateMapper {
    List<TemplateDto> selectAllRoles();
}