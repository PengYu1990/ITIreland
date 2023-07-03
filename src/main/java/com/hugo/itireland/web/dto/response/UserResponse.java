package com.hugo.itireland.web.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter

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

    private int posts;

    private String location;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ctime;

    private String sessionId;

}
