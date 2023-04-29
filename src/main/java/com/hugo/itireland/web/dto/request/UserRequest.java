package com.hugo.itireland.web.dto.request;


import jakarta.persistence.*;
import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String password;
    private String email;
    private String profile;

}
