package com.hugo.itireland.repository;

import com.hugo.itireland.domain.Category;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByCategory(Pageable pageable, Category category);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByCategoryAndState(Pageable pageable, Category category, int state);

    Page<Post> findAllByCategoryAndUserIdAndState(Pageable pageable, Category category, Long userId, int state);
    Page<Post> findAllByUserIdAndState(Pageable pageable, Long userId, int state);

    int countByUser(User user);

    Page<Post> findAllByState(int state, Pageable pageable);
}
