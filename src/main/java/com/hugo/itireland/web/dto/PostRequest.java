package com.hugo.itireland.web.dto;

import com.hugo.itireland.domain.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostRequest {
    private Long userId;
    private String title;
    private String content;
    private List<String> tags;

}
