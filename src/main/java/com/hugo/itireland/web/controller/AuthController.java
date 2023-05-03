package com.hugo.itireland.web.controller;

import com.hugo.itireland.domain.User;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.common.MySessionContext;
import com.hugo.itireland.web.dto.request.LoginRequest;
import com.hugo.itireland.web.dto.request.UserRequest;
import com.hugo.itireland.web.dto.response.UserResponse;
import com.hugo.itireland.web.common.R;
import com.hugo.itireland.web.exception.ApiRequestException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public R register(@RequestBody UserRequest userRequest, HttpSession session){

        // Check if user exist
        if(userService.exist(userRequest.getUsername())){
            throw new ApiRequestException("Username has been used");
        }

        // register
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        user = userService.add(user);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        // set sessionId
        session.setAttribute("user", userResponse);
        userResponse.setSessionId(session.getId());
        return R.success(userResponse);

    }

    @PostMapping("/login")
    public R login(@RequestBody LoginRequest login, HttpSession session){

        // Check if user exist
        if(!userService.exist(login.getUsername())){
            throw new ApiRequestException("The username doesn't exist!");
        }

        User user;

        // Check if password is correct
        if((user = userService.login(login.getUsername(), login.getPassword())) == null){
            throw new ApiRequestException("The password is not correct");
        }

        // login
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        session.setAttribute("user", userResponse);
        // set sessionId
        userResponse.setSessionId(session.getId());
        return R.success(userResponse);

    }


    @PostMapping("/logout")
    public R logout(@RequestParam  String sessionId){
        HttpSession session = MySessionContext.getSession(sessionId);
        if(session!=null){
            session.removeAttribute("user");
        }
        return R.success(null);
    }
}
