package com.plands.backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResult<T> {
    private List<T> content;  // 데이터 리스트
    private int page;         // 현재 페이지 (0부터 시작)
    private int size;         // 페이지 크기
    private int totalElements;// 전체 데이터 수
    private int totalPages;   // 전체 페이지 수

    public PageResult(List<T> content, int page, int size, int totalElements) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
    }

}
