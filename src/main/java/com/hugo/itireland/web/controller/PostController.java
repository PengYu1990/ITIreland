package com.hugo.itireland.web.controller;


import com.hugo.itireland.domain.Category;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.Tag;
import com.hugo.itireland.domain.User;
import com.hugo.itireland.service.PostService;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.dto.request.PostRequest;
import com.hugo.itireland.web.dto.response.PostResponse;
import com.hugo.itireland.web.util.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public R add(@RequestBody PostRequest postRequest) {
        try {

            // convert PostRequest to Post
            Post post = new Post();
            BeanUtils.copyProperties(postRequest, post);

            // associate suer
            User user = userService.findById(postRequest.getUserId());
            post.setUser(user);


            // Process Tags
            List<Tag> tags = new ArrayList<>();
            for (String t : postRequest.getTags()) {
                Tag tag = new Tag();
                tag.setTag(t);
                tags.add(tag);
            }
            post.setTags(tags);

            // Process category
            Category category = new Category(postRequest.getCategory());
            post.setCategory(category);


            // save post
            post = postService.add(post);

            // convert Post to PostResponse, return post to client
            PostResponse postResponse = new PostResponse();
            BeanUtils.copyProperties(post, postResponse);
            postResponse.setUser(user);
            return R.success(postResponse);
        } catch (Exception e) {
            return R.error(400, e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        try {
            PostResponse postResponse = new PostResponse();
            Post post = postService.findById(id);
            BeanUtils.copyProperties(post, postResponse);
            return R.success(postResponse);
        } catch (Exception e) {
            return R.error(400, e.getMessage());
        }
    }


    @GetMapping
    public R find(@RequestParam(defaultValue = "0", required = false) Integer page,
                  @RequestParam(defaultValue = "20", required = false) Integer size,
                  @RequestParam(defaultValue = "id", required = false) String sort) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
            List<Post> posts = postService.findAll(pageable);
            List<PostResponse> postResponses = new ArrayList<>();
            for (Post post : posts) {
                PostResponse postResponse = new PostResponse();
                BeanUtils.copyProperties(post, postResponse);
                postResponses.add(postResponse);
            }
            return R.success(postResponses);
        } catch (Exception e) {
            return R.error(400, e.getMessage());
        }
    }
}
