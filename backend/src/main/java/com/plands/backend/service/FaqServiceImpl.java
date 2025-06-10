package com.plands.backend.service;

import com.plands.backend.common.dto.PageResult;
import com.plands.backend.dto.response.FaqResponseDto;
import com.plands.backend.mapper.FaqMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FaqServiceImpl implements FaqService {

    private final FaqMapper faqMapper;

    public FaqServiceImpl(FaqMapper faqMapper) {
        this.faqMapper = faqMapper;
    }

    @Override
    public PageResult<FaqResponseDto> getFaqs(int page, int size, String keyword) {
        int offset = page * size;
        List<FaqResponseDto> faqs = faqMapper.selectFaqs(offset, size, keyword);
        int totalCount = faqMapper.countFaqs(keyword);

        return new PageResult<>(faqs, page, size, totalCount);
    }
}
