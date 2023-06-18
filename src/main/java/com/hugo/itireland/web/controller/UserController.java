package com.hugo.itireland.web.controller;

import com.hugo.itireland.domain.User;
import com.hugo.itireland.web.dto.response.UserResponse;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.common.R;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    public R findAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                              @RequestParam(defaultValue = "20", required = false) Integer size,
                              @RequestParam(defaultValue = "id", required = false) String sort
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<UserResponse> userResponses =  userService.findAll(pageable);
        return R.success(userResponses, userResponses.getTotalPages(),
                userResponses.getTotalElements(),
                userResponses.getPageable().getPageNumber());

    }


    @GetMapping("/{id}")
    public R findById(@PathVariable Long id){
        return R.success(userService.findById(id));
    }




}
