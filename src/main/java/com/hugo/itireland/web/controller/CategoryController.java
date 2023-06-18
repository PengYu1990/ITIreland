package com.hugo.itireland.web.controller;


import com.hugo.itireland.service.CategoryService;
import com.hugo.itireland.web.common.R;
import com.hugo.itireland.web.dto.request.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;


    @GetMapping
    public R findAll(){
        return R.success(categoryService.findAll());

    }

    @PostMapping
    public R add(@RequestBody CategoryRequest categoryRequest){

        return R.success(categoryService.add(categoryRequest));
    }
}
