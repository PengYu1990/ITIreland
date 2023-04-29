package com.hugo.itireland.repository;

import com.hugo.itireland.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {
    Category findByCategory(String category);
}
