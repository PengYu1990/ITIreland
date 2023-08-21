package com.hugo.itireland.web.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {

    private Long id;

    @NotBlank(message = "Comment content can't be null")
    private String content;

    @NotNull(message = "userId can't be null")
    private Long userId;
    @NotNull(message = "postId content can't be null")
    private Long postId;
    private Long parentId;
}
