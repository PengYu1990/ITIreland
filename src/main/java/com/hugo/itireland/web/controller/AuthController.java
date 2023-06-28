package com.hugo.itireland.web.controller;

import com.hugo.itireland.service.AuthService;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.common.R;
import com.hugo.itireland.web.dto.request.LoginRequest;
import com.hugo.itireland.web.dto.request.RegisterRequest;
import com.hugo.itireland.web.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.hugo.itireland.exception.ValidationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final AuthService authService;

    @PostMapping("/signup")
    public R register(@Validated @RequestBody RegisterRequest registerRequest, BindingResult errors){
        //Throw Validation Exception
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
       AuthResponse authResponse = authService.register(registerRequest);
       return R.success(authResponse);
    }



    @PostMapping("/login")
    public R<AuthResponse> login(@Validated @RequestBody LoginRequest loginRequest, BindingResult errors){
        //Throw Validation Exception
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }

        return R.success(authService.login(loginRequest));

    }


    @PostMapping("/logout")
    public R logout(){
        authService.logout();
        return R.success(null);
    }
}
