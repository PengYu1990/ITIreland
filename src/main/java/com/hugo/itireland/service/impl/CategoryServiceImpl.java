package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.Category;
import com.hugo.itireland.repository.CategoryRepository;
import com.hugo.itireland.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Category add(Category category) {
        return categoryRepository.save(category);
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
}
