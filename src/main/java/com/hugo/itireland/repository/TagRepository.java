package com.hugo.itireland.repository;

import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    Tag findByTag(String tag);

    List<Tag> findAllByTagIn(List<String> tags);
}
