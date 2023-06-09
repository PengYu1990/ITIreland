package com.hugo.itireland.repository;

import com.hugo.itireland.domain.Category;
import com.hugo.itireland.domain.Following;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.User;
import com.hugo.itireland.web.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {

    @Query("SELECT f.follower FROM Following f WHERE f.following = :user")
    Page<User> findFollowersByUser(User user, Pageable pageable);

    @Query("SELECT f.following FROM Following f WHERE f.follower = :user")
    Page<User> findFollowingsByUser(User user, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user IN (SELECT f.following FROM Following f WHERE f.follower = :user) and p.state=0")
    Page<Post> findPostsOfFollowingUsers(@Param("user") User user, Pageable pageable);


    Optional<Following> findByFollowingAndFollower(User followingUser, User followerUser);

    @Query("SELECT p FROM Post p WHERE p.user IN (SELECT f.following FROM Following f WHERE f.follower = :user) and p.category=:cat and p.state=0")
    Page<Post> findPostsOfFollowingUsersAndCategory(User user, Category cat, Pageable pageable);
}
