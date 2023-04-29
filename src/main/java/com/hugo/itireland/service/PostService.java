package com.hugo.itireland.service;

import com.hugo.itireland.domain.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    Post add(Post post);

    Post findById(Long id);

    List<Post> findAll(Pageable pageable);
}
