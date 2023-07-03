package com.hugo.itireland.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hugo.itireland.domain.Tag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostResponse {
    private Long id;
    private UserResponse user;
    private String title;
    private String content;
    private String category;
    private int commentCount;

    private boolean isUpvoted;
    private boolean isDownvoted;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ctime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime utime;

    private int views;
    private int upvotes;
    private int downvotes;
    private List<Tag> tags;

}
