package com.hugo.itireland.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hugo.itireland.domain.Category;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.Tag;
import com.hugo.itireland.domain.User;
import com.hugo.itireland.repository.PostRepository;
import com.hugo.itireland.repository.UserRepository;
import com.hugo.itireland.service.CategoryService;
import com.hugo.itireland.service.PostService;
import com.hugo.itireland.service.TagService;
import com.hugo.itireland.exception.ApiRequestException;
import com.hugo.itireland.web.dto.request.PostRequest;
import com.hugo.itireland.web.dto.response.PostResponse;
import com.hugo.itireland.web.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TagService tagService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;



    @Override
    public PostResponse save(PostRequest postRequest, String username) {

        //convert PostRequest to Post
        Post post;
        User user = userRepository.findById(postRequest.getUserId()).orElseThrow();
        if(postRequest.getId() != null) {
            post = postRepository.findById(postRequest.getId()).orElseThrow();
            if(user == null || user.getId() != post.getUser().getId()){
                throw new ApiRequestException("Sorry, you can only edit your own post.");
            }
        } else {
            post = new Post();
        }
        BeanUtils.copyProperties(postRequest, post);

        // associate user
        post.setUser(user);

        String contentJsonString = null;
        try {
            contentJsonString = objectMapper.writeValueAsString(postRequest.getContentNode());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        post.setContent(contentJsonString);


        // Process category
        if(postRequest.getCategory() == null) {
            throw new IllegalArgumentException("Category can not be null");
        }
        Category category = categoryService.find(postRequest.getCategory());
        if(category == null)
            throw new IllegalArgumentException("Category doesn't exist");
        post.setCategory(category);


        // Process Tags
        if(postRequest.getTags() != null) {
            List<Tag> tags = new ArrayList<>();
            for (String t : postRequest.getTags()) {
                Tag tag = new Tag();
                tag.setTag(t);
                // Process duplicated tag: replace duplicated data with the data from database
                Tag oldTag = tagService.find(tag);
                if(oldTag != null) {
                    tags.add(oldTag);
                } else {
                    tags.add(tag);
                }
            }
            post.setTags(tags);
        }

        // Add Time
        post.setCtime(LocalDateTime.now());
        post.setUtime(LocalDateTime.now());

        // Sava post
        post =  postRepository.save(post);


        // convert Post to PostResponse, return post to client
        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(post, postResponse);

        // process UserResponse
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        postResponse.setUser(userResponse);

        // Process Category
        postResponse.setCategory(post.getCategory().getCategory());

        return postResponse;
    }

    @Override
    public PostResponse findById(Long id) {
        PostResponse postResponse = new PostResponse();
        Post post = postRepository.findById(id).orElseThrow();

        post.setViews(post.getViews()+1);
        updateViews(post);

        BeanUtils.copyProperties(post, postResponse);

        // Process Category
        postResponse.setCategory(post.getCategory().getCategory());

        // process UserResponse for post
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(post.getUser(), userResponse);
        postResponse.setUser(userResponse);

        return postResponse;
    }

    @Override
    public Page<PostResponse> findAll(Pageable pageable, String category) {
        Category cat = null;
        Page<Post> posts;
        if(category != null) {
            cat = categoryService.find(category);
        }
        if(cat != null ) {
            posts = postRepository.findAllByCategory(pageable, cat);
        } else {
            posts = postRepository.findAll(pageable);
        }

        Page<PostResponse> postResponses = posts.map((new Function<Post, PostResponse>() {
                    @Override
                    public PostResponse apply(Post post) {
                        PostResponse postResponse = new PostResponse();
                        BeanUtils.copyProperties(post, postResponse);
                        // Process CategoryResponse
                        postResponse.setCategory(post.getCategory().getCategory());

                        // process UserResponse for posts
                        UserResponse userResponse = new UserResponse();
                        BeanUtils.copyProperties(post.getUser(), userResponse);
                        postResponse.setUser(userResponse);
                        return postResponse;
                    }
                }));

        return postResponses;
    }

    @Override
    public void updateViews(Post post) {
        postRepository.save(post);
    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id).get();
        if(post == null) {
            throw  new ApiRequestException("Post doesn't exist!");
        }

        post.setState(-1);
        postRepository.save(post);

    }
}
