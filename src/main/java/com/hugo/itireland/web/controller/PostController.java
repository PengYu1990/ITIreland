package com.hugo.itireland.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hugo.itireland.domain.*;
import com.hugo.itireland.service.PostService;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.common.MySessionContext;
import com.hugo.itireland.web.dto.request.PostRequest;
import com.hugo.itireland.web.dto.response.CategoryResponse;
import com.hugo.itireland.web.dto.response.CommentResponse;
import com.hugo.itireland.web.dto.response.PostResponse;
import com.hugo.itireland.web.common.R;
import com.hugo.itireland.web.dto.response.UserResponse;
import com.hugo.itireland.web.exception.ApiRequestException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;
    private UserService userService;

    private  ObjectMapper objectMapper;

    @Autowired
    public PostController(PostService postService, UserService userService, ObjectMapper objectMapper) {
        this.postService = postService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/save")
    public R add(@RequestBody PostRequest postRequest, @RequestParam String sessionId) throws JsonProcessingException {

        //convert PostRequest to Post
        Post post;
        if(postRequest.getId() != null) {
            post = postService.findById(postRequest.getId());
            HttpSession session = MySessionContext.getSession(sessionId);
            UserResponse userResponse = (UserResponse) session.getAttribute("user");
            if(userResponse.getId() != post.getUser().getId()){
                throw new ApiRequestException("Sorry, you can only edit your own post.");
            }
        } else {
            post = new Post();
        }
        BeanUtils.copyProperties(postRequest, post);
        String contentJsonString = objectMapper.writeValueAsString(postRequest.getContentNode());
        post.setContent(contentJsonString);

        // associate suer
        User user = userService.findById(postRequest.getUserId());
        post.setUser(user);


        // Process category
        if(postRequest.getCategory() == null) {
            throw new IllegalArgumentException("Category can not be null");
        }
        Category category = new Category(postRequest.getCategory());
        post.setCategory(category);


        // Process Tags
        if(postRequest.getTags() != null) {
            List<Tag> tags = new ArrayList<>();
            for (String t : postRequest.getTags()) {
                Tag tag = new Tag();
                tag.setTag(t);
                tags.add(tag);
            }
            post.setTags(tags);
        }

        // save post
        post = postService.save(post);

        // convert Post to PostResponse, return post to client
        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(post, postResponse);

        // process UserResponse
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        postResponse.setUser(userResponse);

        return R.success(postResponse);

    }


    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        PostResponse postResponse = new PostResponse();
        Post post = postService.findById(id);

        post.setViews(post.getViews()+1);
        postService.updateViews(post);

        BeanUtils.copyProperties(post, postResponse);

        // process CommentResponse
        List<CommentResponse> commentResponses = new ArrayList<>();
        for(Comment comment : post.getComments()){
            CommentResponse commentResponse = new CommentResponse();
            BeanUtils.copyProperties(comment,commentResponse);
            commentResponses.add(commentResponse);

            // process UserResponse for comments
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(post.getUser(), userResponse);
            commentResponse.setUser(userResponse);
        }
        postResponse.setComments(commentResponses);

        // process UserResponse for post
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(post.getUser(), userResponse);
        postResponse.setUser(userResponse);

        return R.success(postResponse);

    }


    @GetMapping
    public R find(@RequestParam(defaultValue = "0", required = false) Integer page,
                  @RequestParam(defaultValue = "20", required = false) Integer size,
                  @RequestParam(required = false) String category,
                  @RequestParam(required = false, defaultValue = "utime") String sorting
                  ) {
        Page<Post> posts;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorting).descending());
        if(category != null && !category.equals("")){
            Category cat = new Category(category);
            posts = postService.findAllByCategory(pageable,cat);
        } else {
            posts = postService.findAll(pageable);
        }


        // Process CommentResponse
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            PostResponse postResponse = new PostResponse();
            BeanUtils.copyProperties(post, postResponse);

            // Process CategoryResponse
            postResponse.setCategory(post.getCategory().getCategory());

            // Process CommentResponse
            List<CommentResponse> commentResponses = new ArrayList<>();
            for(Comment comment : post.getComments()){
                CommentResponse commentResponse = new CommentResponse();
                BeanUtils.copyProperties(comment,commentResponse);
                commentResponses.add(commentResponse);

                // process UserResponse for comments
                UserResponse userResponse = new UserResponse();
                BeanUtils.copyProperties(post.getUser(), userResponse);
                commentResponse.setUser(userResponse);
            }
            postResponse.setComments(commentResponses);


            // process UserResponse for posts
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(post.getUser(), userResponse);
            postResponse.setUser(userResponse);

            postResponses.add(postResponse);
        }
        return R.success(postResponses, posts.getTotalPages(),
                posts.getTotalElements(),
                posts.getPageable().getPageNumber());

    }
}
