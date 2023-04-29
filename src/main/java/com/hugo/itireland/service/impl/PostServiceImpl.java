package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.Tag;
import com.hugo.itireland.repository.PostRepository;
import com.hugo.itireland.service.PostService;
import com.hugo.itireland.service.TagService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private TagService tagService;

    public PostServiceImpl(PostRepository postRepository,
                           TagService tagService
                           ){
        this.postRepository = postRepository;
        this.tagService = tagService;
    }
    @Override
    public Post add(Post post) {
        post.setCtime(LocalDateTime.now());
        post.setUtime(LocalDateTime.now());

        // Process duplicated tag: replace duplicated data with the data from database
        List<Tag> tags = post.getTags();
        List<Tag> saveTags = new ArrayList<>();
        for(Tag tag : tags){
            Tag oldTag = tagService.find(tag);
            if(oldTag != null) {
                tag = oldTag;
            }
            saveTags.add(tag);
        }
        post.setTags(saveTags);

        // Sava post
        post =  postRepository.save(post);
        return post;
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public List<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).toList();
    }
}
