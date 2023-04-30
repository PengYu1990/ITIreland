package com.hugo.itireland.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hugo.itireland.domain.Category;
import com.hugo.itireland.domain.Tag;
import com.hugo.itireland.domain.User;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {
    private Long id;
    private User user;
    private String title;
    private String content;
    private Category category;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ctime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime utime;

    private int views;
    private int thumbs;
    private List<Tag> tags;

}
