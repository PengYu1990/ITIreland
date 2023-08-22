package com.hugo.itireland.web.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@RequiredArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {
    private Long id;

    @NotBlank(message = "Username can't be null")
    private String username;
    @NotBlank(message = "Password can't be null")
    private String password;
    @NotBlank(message = "Email can't be null")
    private String email;
    private String profile;
}
