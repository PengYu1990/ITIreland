package com.hugo.itireland.repository;

import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
