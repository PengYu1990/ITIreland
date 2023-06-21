package com.hugo.itireland.web.controller;


import com.hugo.itireland.service.FollowingService;
import com.hugo.itireland.web.common.R;
import com.hugo.itireland.web.dto.request.FollowRequest;
import com.hugo.itireland.web.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowingController {

    private final FollowingService followingService;


    @PostMapping("/follow")
    public R follow(@RequestBody FollowRequest followRequest){
        followingService.follow(followRequest);
        return R.success(null);
    }

    @PostMapping("/unfollow")
    public R unFollow(@RequestBody FollowRequest followRequest){
        followingService.unFollow(followRequest);
        return R.success(null);
    }

    @GetMapping("/followers/{userId}")
    public R followers(@PathVariable(name = "userId") Long userId,
                       @RequestParam(defaultValue = "0", required = false) Integer page,
                       @RequestParam(defaultValue = "20", required = false) Integer size,
                       @RequestParam(required = false, defaultValue = "ctime") String sorting
                       ){
        Pageable pageable = PageRequest.of(page, size,  Sort.by(sorting).descending());
        Page<UserResponse> followers = followingService.getFollowers(userId, pageable);
        return R.success(followers.toList(),
                followers.getTotalPages(),
                followers.getTotalElements(),
                followers.getPageable().getPageNumber());
    }

    @GetMapping("/followings/{userId}")
    public R followings(@PathVariable(name = "userId") Long userId,
                       @RequestParam(defaultValue = "0", required = false) Integer page,
                       @RequestParam(defaultValue = "20", required = false) Integer size,
                       @RequestParam(required = false, defaultValue = "ctime") String sorting
    ){
        Pageable pageable = PageRequest.of(page, size,  Sort.by(sorting).descending());
        Page<UserResponse> followers = followingService.getFollowings(userId, pageable);
        return R.success(followers.toList(),
                followers.getTotalPages(),
                followers.getTotalElements(),
                followers.getPageable().getPageNumber());
    }
}
