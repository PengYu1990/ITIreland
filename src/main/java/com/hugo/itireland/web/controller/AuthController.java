package com.hugo.itireland.web.controller;

import com.hugo.itireland.domain.User;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.common.MySessionContext;
import com.hugo.itireland.web.dto.request.LoginRequest;
import com.hugo.itireland.web.dto.request.UserRequest;
import com.hugo.itireland.web.dto.response.UserResponse;
import com.hugo.itireland.web.common.R;
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
        try {

            // Check if user exist
            if(userService.exist(userRequest.getUsername())){
                return R.error(400, "Username has been used");
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
        } catch (Exception e){
            return R.error(400, e.getMessage());
        }
    }

    @PostMapping("/login")
    public R login(@RequestBody LoginRequest login, HttpSession session){
        try {

            // Check if user exist
            if(!userService.exist(login.getUsername())){
                return R.error(400, "Incorrect username!");
            }

            User user;

            // Check if password is correct
            if((user = userService.login(login.getUsername(), login.getPassword())) == null){
                return R.error(400, "Incorrect password!");
            }

            // login
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(user, userResponse);
            session.setAttribute("user", userResponse);
            // set sessionId
            userResponse.setSessionId(session.getId());
            return R.success(userResponse);
        } catch (Exception e){
            return R.error(400, e.getMessage());
        }
    }


    @PostMapping("/logout")
    public R logout(@RequestParam  String sessionId){
        try {
            HttpSession session = MySessionContext.getSession(sessionId);
            if(session!=null){
                session.removeAttribute("user");
            }
            return R.success(null);
        } catch (Exception exception){
            return R.error(400,"log out error");
        }
    }
}
