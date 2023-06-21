package com.hugo.itireland.service;

import com.hugo.itireland.web.dto.request.FollowRequest;
import com.hugo.itireland.web.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FollowingService {
    public void follow(FollowRequest followRequest);
    public void unFollow(FollowRequest followRequest);

    public Page<UserResponse> getFollowers(Long userId, Pageable pageable);
    public Page<UserResponse> getFollowings(Long userId, Pageable pageable);
}
