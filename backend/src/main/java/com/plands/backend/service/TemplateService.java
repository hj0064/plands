package com.plands.backend.service;

import com.plands.backend.dto.TemplateDto;
import com.plands.backend.mapper.TemplateMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TemplateService {
    private final TemplateMapper templateMapper;

    public TemplateService(TemplateMapper templateMapper) {
        this.templateMapper = templateMapper;
    }

    public List<TemplateDto> getAllRoles() {
        return templateMapper.selectAllRoles();
    }
}
