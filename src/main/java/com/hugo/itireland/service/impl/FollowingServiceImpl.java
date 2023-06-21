package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.Following;
import com.hugo.itireland.domain.User;
import com.hugo.itireland.repository.FollowingRepository;
import com.hugo.itireland.repository.UserRepository;
import com.hugo.itireland.service.FollowingService;
import com.hugo.itireland.web.dto.request.FollowRequest;
import com.hugo.itireland.web.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class FollowingServiceImpl implements FollowingService {


    private final FollowingRepository followingRepository;

    private final UserRepository userRepository;

    @Override
    public void follow(FollowRequest followRequest) {
        User followingUser = userRepository.findById(followRequest.getFollowingId()).orElseThrow();
        User followerUser = userRepository.findById(followRequest.getFollowerId()).orElseThrow();
        Following following = new Following(followingUser, followerUser, LocalDateTime.now());
        followingRepository.save(following);
    }

    @Override
    public void unFollow(FollowRequest followRequest) {
        User followingUser = userRepository.findById(followRequest.getFollowingId()).orElseThrow();
        User followerUser = userRepository.findById(followRequest.getFollowerId()).orElseThrow();
        Following following = new Following(followingUser, followerUser, LocalDateTime.now());
        followingRepository.delete(following);
    }

    @Override
    public Page<UserResponse> getFollowers(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow();
        Page<UserResponse> followers = followingRepository.findFollowersByUser(user, pageable).map(new Function<User, UserResponse>() {
            @Override
            public UserResponse apply(User user) {
                UserResponse userResponse = new UserResponse();
                BeanUtils.copyProperties(user, userResponse);
                return userResponse;
            }
        });

        return followers;

    }

    @Override
    public Page<UserResponse> getFollowings(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow();
        Page<UserResponse> followings = followingRepository.findFollowingsByUser(user, pageable).map(new Function<User, UserResponse>() {
            @Override
            public UserResponse apply(User user) {
                UserResponse userResponse = new UserResponse();
                BeanUtils.copyProperties(user, userResponse);
                return userResponse;
            }
        });

        return followings;
    }
}
