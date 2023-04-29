package com.hugo.itireland.web.dto;


import jakarta.persistence.*;
import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String password;
    private String email;
    private String profile;

    //0:active, 1:disabled, 2:delete
    private int state;

    private int credits;

    private int level;

    private String headShotUrl;

}
