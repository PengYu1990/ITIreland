package com.hugo.itireland.web.dto.response;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hugo.itireland.domain.Comment;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CommentResponse {

    private Long id;
    private String content;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ctime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime utime;

    private UserResponse user;

    private Long postId;

    private List<CommentResponse> childrenComments;

}
