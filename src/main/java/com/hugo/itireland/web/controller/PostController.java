package com.hugo.itireland.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hugo.itireland.domain.Category;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.Tag;
import com.hugo.itireland.domain.User;
import com.hugo.itireland.exception.ApiRequestException;
import com.hugo.itireland.service.PostService;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.common.R;
import com.hugo.itireland.web.dto.request.PostRequest;
import com.hugo.itireland.web.dto.response.PostResponse;
import com.hugo.itireland.web.dto.response.UserResponse;
import com.hugo.itireland.web.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    private final ObjectMapper objectMapper;

    private final JwtService jwtService;

    // remove save
    @PostMapping
    public R save(HttpServletRequest request, @RequestBody PostRequest postRequest) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return R.success(postService.save(postRequest, authentication.getName()));
    }


    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {

        return R.success(postService.findById(id));

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


    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id){
        postService.delete(id);
        return R.success(null);
    }
}
