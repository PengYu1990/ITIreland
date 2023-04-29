package com.hugo.itireland.web.dto.response;

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
    private LocalDateTime ctime;
    private LocalDateTime utime;

    private int views;
    private int thumbs;
    @OneToMany
    private List<Tag> tags;

}
