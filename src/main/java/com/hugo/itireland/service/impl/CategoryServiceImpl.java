package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.Category;
import com.hugo.itireland.repository.CategoryRepository;
import com.hugo.itireland.service.CategoryService;
import com.hugo.itireland.web.dto.request.CategoryRequest;
import com.hugo.itireland.web.dto.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse add(CategoryRequest categoryRequest) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryRequest, category);
        CategoryResponse commentResponse = new CategoryResponse();
        BeanUtils.copyProperties(category, commentResponse);
        category = categoryRepository.save(category);
        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(category, categoryResponse);
        return categoryResponse;
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public Category update(Category category) {
        Category oldCategory = categoryRepository.findByCategory(category.getCategory());
        oldCategory.setCategory(category.getCategory());
        return categoryRepository.save(oldCategory);
    }

    @Override
    public Category find(String category) {
        return categoryRepository.findByCategory(category);
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAllByOrderBySortDesc().stream().map(category -> {
            CategoryResponse categoryResponse = new CategoryResponse();
            BeanUtils.copyProperties(category, categoryResponse);
            return categoryResponse;
        }).collect(Collectors.toList());
    }
}
