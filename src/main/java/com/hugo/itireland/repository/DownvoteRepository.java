package com.hugo.itireland.repository;

import com.hugo.itireland.domain.Downvote;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.Upvote;
import com.hugo.itireland.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DownvoteRepository extends JpaRepository<Downvote, Long> {

    Downvote findByPostAndUser(Post post, User user);
}
