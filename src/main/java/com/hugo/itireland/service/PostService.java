package com.hugo.itireland.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hugo.itireland.domain.Category;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.web.dto.request.PostRequest;
import com.hugo.itireland.web.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostResponse save(PostRequest postRequest, String username) throws JsonProcessingException;

    PostResponse findById(Long id);

    Page<PostResponse> findAll(Pageable pageable, String category, Long userId);

    void updateViews(Post post);

    void delete(Long id);

    Page<PostResponse> findAllFollowingPosts(Long userId, Pageable pageable);
}
