package com.hugo.itireland.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.web.dto.request.PostRequest;
import com.hugo.itireland.web.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostResponse save(PostRequest postRequest, String username) throws JsonProcessingException;

    PostResponse findById(Long id);

    Page<PostResponse> findAll(Pageable pageable, String category, Long userId);

    void updateViews(Post post);

    void delete(Long id);

    Page<PostResponse> findAllFollowingPosts(String username, Pageable pageable);

    int upvote(Long postId);

    int unUpvote(Long postId);

    int downvote(Long postId);

    int unDownvote(Long postId);
}
