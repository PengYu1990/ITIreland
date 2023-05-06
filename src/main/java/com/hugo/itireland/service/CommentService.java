package com.hugo.itireland.service;

import com.hugo.itireland.domain.Comment;
import com.hugo.itireland.domain.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    Comment add(Comment comment);

    Comment findById(Long id);

    List<Comment> findAll(Pageable pageable);

    void delete(Long id);
}
