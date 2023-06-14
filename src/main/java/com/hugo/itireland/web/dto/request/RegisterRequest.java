package com.hugo.itireland.web.dto.request;


import lombok.*;

@RequiredArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {
    private Long id;

    private String username;
    private String password;
    private String email;
    private String profile;
}
