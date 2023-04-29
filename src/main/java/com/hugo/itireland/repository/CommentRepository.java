package com.hugo.itireland.repository;

import com.hugo.itireland.domain.Comment;
import com.hugo.itireland.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
