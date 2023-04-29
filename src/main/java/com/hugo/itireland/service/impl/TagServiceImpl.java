package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.Tag;
import com.hugo.itireland.repository.TagRepository;
import com.hugo.itireland.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;


    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag save(Tag tag) {
        var oldTag = tagRepository.findByTag(tag.getTag());
        if (oldTag == null)
            oldTag = tagRepository.save(tag);
        return oldTag;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> findAll(List<String> tags) {
        return tagRepository.findAllByTagIn(tags);
    }

    @Override
    public Tag find(Tag tag) {
        return tagRepository.findByTag(tag.getTag());
    }
}
