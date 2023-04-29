package com.hugo.itireland.service;

import com.hugo.itireland.domain.Category;

public interface CategoryService {
    Category add(Category category);
    void delete(Category category);

    Category update(Category category);

    Category find(String category);
}
