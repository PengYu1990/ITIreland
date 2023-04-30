package com.hugo.itireland.web.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {

    private Long id;
    private String content;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ctime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime utime;

    private User user;
    private Post post;
}
