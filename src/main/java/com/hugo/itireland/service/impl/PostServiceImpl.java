package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.Category;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.Tag;
import com.hugo.itireland.repository.PostRepository;
import com.hugo.itireland.service.CategoryService;
import com.hugo.itireland.service.PostService;
import com.hugo.itireland.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private TagService tagService;
    private CategoryService categoryService;

    public PostServiceImpl(PostRepository postRepository,
                           TagService tagService,
                           CategoryService categoryService
                           ){
        this.postRepository = postRepository;
        this.tagService = tagService;
        this.categoryService = categoryService;
    }
    @Override
    public Post save(Post post) {
        post.setCtime(LocalDateTime.now());
        post.setUtime(LocalDateTime.now());

        // Process duplicated tag: replace duplicated data with the data from database

        List<Tag> tags = post.getTags();
        if(tags != null) {
            List<Tag> saveTags = new ArrayList<>();
            for (Tag tag : tags) {
                Tag oldTag = tagService.find(tag);
                if (oldTag != null) {
                    tag = oldTag;
                }
                saveTags.add(tag);
            }
            post.setTags(saveTags);
        }

        // Process category
        Category category = categoryService.find(post.getCategory().getCategory());
        if(category == null)
            throw new IllegalArgumentException("Category doesn't exist");
        post.setCategory(category);


        // Sava post
        post =  postRepository.save(post);
        return post;
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAllByState(pageable,0);
    }

    @Override
    public Page<Post> findAllByCategory(Pageable pageable, Category category) {
        return postRepository.findAllByCategoryAndState(pageable, category, 0);
    }

    @Override
    public void updateViews(Post post) {
        postRepository.save(post);
    }
}
