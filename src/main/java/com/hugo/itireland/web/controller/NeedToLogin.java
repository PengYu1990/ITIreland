package com.hugo.itireland.web.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/needLogin")
public class NeedToLogin {

    @GetMapping
    public String needLogin(){
        return "Need Login";
    }
}
