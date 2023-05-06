package com.hugo.itireland.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hugo.itireland.domain.Category;
import com.hugo.itireland.domain.Comment;
import com.hugo.itireland.domain.Tag;
import com.hugo.itireland.domain.User;
import jakarta.persistence.OneToMany;
import lombok.Data;
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ctime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime utime;

    private int views;
    private int thumbs;
    private List<Tag> tags;

}
