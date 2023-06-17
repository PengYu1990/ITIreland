package com.hugo.itireland.service;

import com.hugo.itireland.domain.Category;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.web.dto.request.PostRequest;
import com.hugo.itireland.web.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostResponse save(PostRequest postRequest, String username);

    PostResponse findById(Long id);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByCategory(Pageable pageable, Category category);

    void updateViews(Post post);

    void delete(Long id);
}
