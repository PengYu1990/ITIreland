package com.hugo.itireland.web.controller;

import com.hugo.itireland.service.AuthService;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.common.R;
import com.hugo.itireland.web.dto.request.LoginRequest;
import com.hugo.itireland.web.dto.request.RegisterRequest;
import com.hugo.itireland.web.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final AuthService authService;

    @PostMapping("/signup")
    public R register(@RequestBody RegisterRequest registerRequest){

       AuthResponse authResponse = authService.register(registerRequest);
       return R.success(authResponse);
    }



    @PostMapping("/login")
    public R<AuthResponse> login(@RequestBody LoginRequest loginRequest){

        return R.success(authService.login(loginRequest));

    }


    @PostMapping("/logout")
    public R logout(@RequestParam String token){
        authService.logout(token);
        return R.success(null);
    }
}
