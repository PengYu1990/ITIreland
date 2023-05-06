package com.hugo.itireland.repository;

import com.hugo.itireland.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByCategory(String category);

    List<Category> findAllByOrderBySortDesc();
}
