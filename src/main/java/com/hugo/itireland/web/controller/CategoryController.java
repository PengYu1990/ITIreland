package com.hugo.itireland.web.controller;


import com.hugo.itireland.domain.Category;
import com.hugo.itireland.service.CategoryService;
import com.hugo.itireland.web.dto.request.CategoryRequest;
import com.hugo.itireland.web.dto.response.CategoryResponse;
import com.hugo.itireland.web.util.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;


    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping
    public R add(@RequestBody CategoryRequest categoryRequest){
        try {
            Category category = new Category();
            BeanUtils.copyProperties(categoryRequest, category);
            category = categoryService.add(category);
            CategoryResponse commentResponse = new CategoryResponse();
            BeanUtils.copyProperties(category, commentResponse);
            return R.success(commentResponse);
        } catch (Exception e){
            return R.error(400, e.getMessage());
        }
    }
}
