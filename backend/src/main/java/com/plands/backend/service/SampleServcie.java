package com.plands.backend.service;

import com.plands.backend.domain.Sample;
import com.plands.backend.mapper.SampleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleServcie {
    private final SampleMapper sampleMapper;

    public SampleServcie(SampleMapper sampleMapper) {
        this.sampleMapper = sampleMapper;
    }

    public List<Sample> getAllUsers() {
        return sampleMapper.findAll();
    }
}
