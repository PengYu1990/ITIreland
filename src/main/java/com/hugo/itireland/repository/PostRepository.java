package com.hugo.itireland.repository;

import com.hugo.itireland.domain.Category;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCategory(Pageable pageable, Category category);
}
