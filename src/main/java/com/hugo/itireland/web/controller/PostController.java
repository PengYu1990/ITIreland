package com.hugo.itireland.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hugo.itireland.exception.ValidationException;
import com.hugo.itireland.s3.S3Buckets;
import com.hugo.itireland.s3.S3Service;
import com.hugo.itireland.service.PostService;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.common.R;
import com.hugo.itireland.web.dto.request.PostRequest;
import com.hugo.itireland.web.dto.response.PostResponse;
import com.hugo.itireland.web.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

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
    public R save(HttpServletRequest request, @Validated  @RequestBody PostRequest postRequest, BindingResult errors) throws JsonProcessingException {
        //Throw Validation Exception
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
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
                  @RequestParam(required = false) Long userId,
                  @RequestParam(required = false, defaultValue = "utime") String sorting
                  ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorting).descending());

        Page<PostResponse> postResponses = postService.findAll(pageable, category, userId);


        return R.success(postResponses.stream().toList(), postResponses.getTotalPages(),
                postResponses.getTotalElements(),
                postResponses.getPageable().getPageNumber());

    }

    @GetMapping("/following/{userId}")
    public R following(@PathVariable Long userId,
                       @RequestParam(defaultValue = "0", required = false) Integer page,
                       @RequestParam(defaultValue = "20", required = false) Integer size,
                       @RequestParam(required = false, defaultValue = "utime") String sorting){
        Pageable pageable = PageRequest.of(page,size,Sort.by(sorting).descending());
        Page<PostResponse> postResponses = postService.findAllFollowingPosts(userId, pageable);
        return R.success(postResponses.toList(),
                postResponses.getTotalPages(),
                postResponses.getTotalElements(),
                postResponses.getPageable().getPageNumber());
    }


    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id){
        postService.delete(id);
        return R.success(null);
    }




    //c466729d-783d-4022-b89a-421544aaabab
}
