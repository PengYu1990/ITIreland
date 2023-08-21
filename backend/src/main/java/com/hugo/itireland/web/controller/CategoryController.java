package com.hugo.itireland.web.controller;


import com.hugo.itireland.service.CategoryService;
import com.hugo.itireland.web.common.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;


    @GetMapping
    public R findAll(){
        return R.success(categoryService.findAll());

    }
}
