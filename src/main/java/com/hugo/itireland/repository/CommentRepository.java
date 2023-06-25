package com.hugo.itireland.repository;

import com.hugo.itireland.domain.Comment;
import com.hugo.itireland.domain.Post;
import org.springframework.data.domain.ManagedTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByState(Pageable pageable, int state);

    List<Comment> findAllByPostIdAndState(Pageable pageable, Long postId, int state);


    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.comments cc WHERE c.parentComment IS NULL and c.state = 0 AND (cc.state = 0 or cc IS NULL)")
    List<Comment> findAllByPostIdAndStateAndParentCommentIsNull(Pageable pageable, Long postId, int state);
}
