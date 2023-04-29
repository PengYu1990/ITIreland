package com.hugo.itireland.web.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRequest {

    private String content;
    private Long userId;
    private Long postId;
}
