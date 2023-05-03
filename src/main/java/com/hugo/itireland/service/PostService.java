package com.hugo.itireland.service;

import com.hugo.itireland.domain.Category;
import com.hugo.itireland.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Post save(Post post);

    Post findById(Long id);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByCategory(Pageable pageable, Category category);

    void updateViews(Post post);
}
