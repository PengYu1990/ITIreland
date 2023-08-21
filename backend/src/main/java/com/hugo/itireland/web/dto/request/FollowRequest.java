package com.hugo.itireland.web.dto.request;


import lombok.Data;

@Data
public class FollowRequest {

    private Long followingId;
    private Long followerId;
}
