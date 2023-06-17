package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.Comment;
import com.hugo.itireland.domain.Post;
import com.hugo.itireland.domain.User;
import com.hugo.itireland.repository.CommentRepository;
import com.hugo.itireland.repository.UserRepository;
import com.hugo.itireland.service.CommentService;
import com.hugo.itireland.exception.ApiRequestException;
import com.hugo.itireland.service.PostService;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.dto.request.CommentRequest;
import com.hugo.itireland.web.dto.response.CommentResponse;
import com.hugo.itireland.web.dto.response.PostResponse;
import com.hugo.itireland.web.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;
    private final PostService postService;

    @Override
    public CommentResponse add(CommentRequest commentRequest) {
        Comment comment;
        if(commentRequest.getId() != null) {
            comment = commentRepository.findById(commentRequest.getId()).orElseThrow();
        } else {
            comment = new Comment();
        }

        BeanUtils.copyProperties(commentRequest, comment);
        User user = userService.findById(commentRequest.getUserId());
        comment.setUser(user);
        comment.setCtime(LocalDateTime.now());
        comment.setUtime(LocalDateTime.now());
        comment = commentRepository.save(comment);

        // Process Return Value
        CommentResponse commentResponse = new CommentResponse();
        BeanUtils.copyProperties(comment, commentResponse);

        // Process UserResponse
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(comment.getUser(), userResponse);
        commentResponse.setUser(userResponse);

        // Process PostId
        PostResponse postResponse = postService.findById(commentRequest.getPostId());
        commentResponse.setPostId(commentRequest.getPostId());

        return commentResponse;
    }

    @Override
    public CommentResponse findById(Long id) {
        Comment comment =  commentRepository.findById(id).orElseThrow(()->{
            return new ApiRequestException("Comment id doesn't exist.");
        });
        CommentResponse commentResponse = new CommentResponse();
        BeanUtils.copyProperties(comment, commentResponse);
        return commentResponse;
    }

    @Override
    public List<Comment> findAll(Pageable pageable) {
        return commentRepository.findAllByState(pageable,0).toList();
    }

    @Override
    public void delete(Long id, String username) {
        Comment comment = commentRepository.findById(id).get();
        if(!comment.getUser().getUsername().equals(username)){
            throw new ApiRequestException("You can't delete a comment which doesn't belong to you!");
        }
        if(comment == null) {
           throw new ApiRequestException("Comment doesn't exist!");
        }
        comment.setState(-1);
        commentRepository.save(comment);
    }

    @Override
    public List<CommentResponse> findAllByPostId(Pageable pageable, Long postId) {
        return commentRepository.findAllByPostIdAndState(pageable, postId, 0).stream().map( comment -> {
                CommentResponse commentResponse = new CommentResponse();
                BeanUtils.copyProperties(comment, commentResponse);
                // Process postId
                commentResponse.setPostId(comment.getPost().getId());
                // Process UserResponse
                UserResponse userResponse = new UserResponse();
                BeanUtils.copyProperties(comment.getUser(), userResponse);
                commentResponse.setUser(userResponse);
                return commentResponse;
        }).collect(Collectors.toList());
    }
}
