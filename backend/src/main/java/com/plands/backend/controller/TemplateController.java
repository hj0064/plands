package com.plands.backend.controller;

import com.plands.backend.dto.TemplateDto;
import com.plands.backend.service.TemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/template/*")
public class TemplateController {
    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping("/getRoleList")
    public List<TemplateDto> listRole() {
        return templateService.getAllRoles();
    }
}
