package com.hugo.itireland.service;

import com.hugo.itireland.domain.Tag;

import java.util.List;

public interface TagService {
    Tag save(Tag tag);

    List<Tag> findAll();
    List<Tag> findAll(List<String> tags);

    Tag find(Tag tag);
}
