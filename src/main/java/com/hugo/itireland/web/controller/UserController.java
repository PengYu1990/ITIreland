package com.hugo.itireland.web.controller;

import com.hugo.itireland.domain.User;
import com.hugo.itireland.web.dto.request.LoginRequest;
import com.hugo.itireland.web.dto.request.UserRequest;
import com.hugo.itireland.web.dto.response.UserResponse;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.util.R;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private UserService userService;

//    public UserController(UserService userService){
//        this.userService = userService;
//    }

    @GetMapping
    public R findAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                              @RequestParam(defaultValue = "20", required = false) Integer size,
                              @RequestParam(defaultValue = "id", required = false) String sort
    ){
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
            List<User> users = userService.findAll(pageable);
            List<UserResponse> userResponses = new ArrayList<>();
            for (User user : users) {
                UserResponse userResponse = new UserResponse();
                BeanUtils.copyProperties(user, userResponse);
                userResponses.add(userResponse);
            }
            return R.success(userResponses);
        } catch (Exception e){
            return R.error(400, e.getMessage());
        }


    }


    @GetMapping("/{id}")
    public R findById(@PathVariable Long id){
        try {
            User user = userService.findById(id);
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(user, userResponse);
            return R.success(userResponse);
        }catch (Exception e){
            return R.error(400, e.getMessage());
        }
    }

    @PostMapping("/register")
    public R register(@RequestBody UserRequest userRequest){
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
            return R.success(userResponse);
        } catch (Exception e){
            return R.error(400, e.getMessage());
        }
    }

    @PostMapping("/login")
    public R login(@RequestBody LoginRequest login){
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
            return R.success(userResponse);
        } catch (Exception e){
            return R.error(400, e.getMessage());
        }
    }


}
