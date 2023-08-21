package com.hugo.itireland.service;

import com.hugo.itireland.web.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FollowingService {
    public void follow(Long userId);
    public void unFollow(Long userId);

    public Page<UserResponse> getFollowers(Long userId, Pageable pageable);
    public Page<UserResponse> getFollowings(Long userId, Pageable pageable);

    public boolean isFollowing(Long userId);
}
