package com.hugo.itireland.repository;

import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.Upvote;
import com.hugo.itireland.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpvoteRepository extends JpaRepository<Upvote, Long> {

    Upvote findByPostAndUser(Post post, User user);
}
