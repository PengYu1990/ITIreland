package com.hugo.itireland.repository;

import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, String> {
    Tag findByTag(String tag);

    List<Tag> findAllByTagIn(List<String> tags);
}
