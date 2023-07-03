package com.hugo.itireland.web.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username can't be null!")
    private String username;

    @NotBlank(message = "Password can't be null!")
    private String password;
}
