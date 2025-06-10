package com.plands.backend.dto.request;

import com.plands.backend.enums.NoticeSearchType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoticeSearchRequestDto {
    private int page = 1;
    private int size = 8;
    private String keyword;
    private NoticeSearchType searchType = NoticeSearchType.TITLE_CONTENT;

    public int getOffset() {
        return (page - 1) * size;
    }
}
