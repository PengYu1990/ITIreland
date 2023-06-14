package com.hugo.itireland.web.controller;


import com.hugo.itireland.domain.Comment;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.User;
import com.hugo.itireland.service.CommentService;
import com.hugo.itireland.service.PostService;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.common.R;
import com.hugo.itireland.web.dto.request.CommentRequest;
import com.hugo.itireland.web.dto.response.CommentResponse;
import com.hugo.itireland.web.dto.response.UserResponse;
import com.hugo.itireland.web.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;

    private final JwtService jwtService;


    @PostMapping
    public R add(@RequestBody CommentRequest commentRequest){
        Comment comment;
        if(commentRequest.getId() != null) {
            comment = commentService.findById(commentRequest.getId());
        } else {
            comment = new Comment();
        }
        User user = userService.findById(commentRequest.getUserId());
        Post post = postService.findById(commentRequest.getPostId());
        BeanUtils.copyProperties(commentRequest, comment);
        comment.setUser(user);
        comment.setPost(post);
        comment = commentService.add(comment);
        CommentResponse commentResponse = new CommentResponse();
        BeanUtils.copyProperties(comment, commentResponse);

        // Process UserResponse
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(comment.getUser(), userResponse);
        commentResponse.setUser(userResponse);
        return R.success(commentResponse);
    }


    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id, @RequestHeader String Authorization){
        String username = jwtService.extractUsername(Authorization);
        commentService.delete(id, username);
        return R.success(null);
    }

    @GetMapping("/{id}")
    public R findById(@PathVariable Long id){
        Comment comment = commentService.findById(id);
        CommentResponse commentResponse = new CommentResponse();
        BeanUtils.copyProperties(comment, commentResponse);
        return R.success(commentResponse);
    }


    @GetMapping
    public R find(@RequestParam(defaultValue = "0", required = false) Integer page,
                  @RequestParam(defaultValue = "100", required = false) Integer size,
                  @RequestParam(defaultValue = "id", required = false) String sort,
                  @RequestParam Long postId
                  ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        List<Comment> comments = commentService.findAllByPostId(pageable, postId);
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponse commentResponse = new CommentResponse();
            BeanUtils.copyProperties(comment, commentResponse);

            // Process postId

            commentResponse.setPostId(comment.getPost().getId());

            // Process UserResponse
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(comment.getUser(), userResponse);
            commentResponse.setUser(userResponse);
            commentResponses.add(commentResponse);
        }
        return R.success(commentResponses);

    }
}
