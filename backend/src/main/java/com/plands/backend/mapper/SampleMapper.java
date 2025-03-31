package com.plands.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.plands.backend.domain.Sample;
import java.util.List;

@Mapper
public interface SampleMapper {
    List<Sample> findAll();
}
