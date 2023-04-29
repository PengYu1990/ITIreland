package com.hugo.itireland.web.dto.response;


import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {

    private Long id;
    private String content;
    private LocalDateTime ctime;
    private LocalDateTime utime;

    private User user;
    private Post post;
}
