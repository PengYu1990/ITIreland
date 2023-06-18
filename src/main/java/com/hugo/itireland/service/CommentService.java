package com.hugo.itireland.service;

import com.hugo.itireland.domain.Comment;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.web.dto.request.CommentRequest;
import com.hugo.itireland.web.dto.response.CommentResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    CommentResponse add(CommentRequest commentRequest);

    CommentResponse findById(Long id);

    void delete(Long id, String username);

    List<CommentResponse> findAllByPostId(Pageable pageable, Long postId);
}
