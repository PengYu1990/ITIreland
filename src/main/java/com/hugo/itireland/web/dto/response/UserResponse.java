package com.hugo.itireland.web.dto.response;


import lombok.Data;

import java.time.LocalDateTime;


@Data

public class UserResponse {


    private Long id;

    private String username;
    private String email;

    private String profile;

    //0:active, 1:disabled, 2:delete
    private int state;

    private int credits;

    private int level;

    private String headShotUrl;

    private LocalDateTime ctime;

}
