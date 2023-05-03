package com.hugo.itireland.web.controller;


import com.hugo.itireland.domain.Category;
import com.hugo.itireland.service.CategoryService;
import com.hugo.itireland.web.dto.request.CategoryRequest;
import com.hugo.itireland.web.dto.response.CategoryResponse;
import com.hugo.itireland.web.common.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;


    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }


    @GetMapping
    public R findAll(){
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        List<Category> categories = categoryService.findAll();
        for (Category category : categories) {
            CategoryResponse cr = new CategoryResponse();
            BeanUtils.copyProperties(category, cr);
            categoryResponseList.add(cr);
        }
        return R.success(categoryResponseList);

    }

    @PostMapping
    public R add(@RequestBody CategoryRequest categoryRequest){
        Category category = new Category();
        BeanUtils.copyProperties(categoryRequest, category);
        category = categoryService.add(category);
        CategoryResponse commentResponse = new CategoryResponse();
        BeanUtils.copyProperties(category, commentResponse);
        return R.success(commentResponse);
    }
}
