package com.hugo.itireland.service;

import com.hugo.itireland.domain.Category;
import com.hugo.itireland.web.dto.request.CategoryRequest;
import com.hugo.itireland.web.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse add(CategoryRequest categoryRequest);
    void delete(Category category);

    Category update(Category category);

    Category find(String category);

    List<CategoryResponse> findAll();
}
