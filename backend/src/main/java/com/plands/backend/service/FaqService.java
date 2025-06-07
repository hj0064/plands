package com.plands.backend.service;

import com.plands.backend.common.dto.PageResult;
import com.plands.backend.dto.FaqResponseDto;

public interface FaqService {
    PageResult<FaqResponseDto> getFaqs(int page, int size, String keyword);
}
