package com.hugo.itireland.web.controller;


import com.hugo.itireland.domain.Category;
import com.hugo.itireland.service.CategoryService;
import com.hugo.itireland.web.common.R;
import com.hugo.itireland.web.dto.request.CategoryRequest;
import com.hugo.itireland.web.dto.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
