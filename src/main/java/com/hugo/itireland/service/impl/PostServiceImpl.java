package com.hugo.itireland.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hugo.itireland.domain.*;
import com.hugo.itireland.exception.ApiRequestException;
import com.hugo.itireland.exception.ResourceNotFoundException;
import com.hugo.itireland.repository.*;
import com.hugo.itireland.service.CategoryService;
import com.hugo.itireland.service.PostService;
import com.hugo.itireland.service.TagService;
import com.hugo.itireland.web.dto.request.PostRequest;
import com.hugo.itireland.web.dto.response.PostResponse;
import com.hugo.itireland.web.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final TagService tagService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;

    private final FollowingRepository followingRepository;
    private final UpvoteRepository upvoteRepository;
    private final DownvoteRepository downvoteRepository;
    private final ObjectMapper objectMapper;



    @Override
    public PostResponse save(PostRequest postRequest, String username) throws JsonProcessingException {

        //convert PostRequest to Post
        Post post;
        User user = userRepository.findById(postRequest.getUserId()).orElseThrow();
        if(postRequest.getId() != null) {
            post = postRepository.findById(postRequest.getId()).orElseThrow();
            if(user == null || user.getId() != post.getUser().getId()){
                throw new InsufficientAuthenticationException("Sorry, you can only edit your own post.");
            }
        } else {
            post = new Post();
            // Credits + 5 when a user post a new post
            user.setCredits(user.getCredits() + 5);
        }
        BeanUtils.copyProperties(postRequest, post);

        // associate user
        post.setUser(user);

        String contentJsonString = objectMapper.writeValueAsString(postRequest.getContentNode());

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

//        BeanUtils.copyProperties(post, postResponse);
//
//        // Process Category
//        postResponse.setCategory(post.getCategory().getCategory());
//
//        // process UserResponse for post
//        UserResponse userResponse = new UserResponse();
//        BeanUtils.copyProperties(post.getUser(), userResponse);
//        userResponse.setPosts(postRepository.countByUser(post.getUser()));
//        postResponse.setUser(userResponse);

        return getPostResponse(post);
    }

    @Override
    public Page<PostResponse> findAll(Pageable pageable, String category, Long userId) {
        Category cat = null;
        Page<Post> posts = null;
        if(category != null) {
            cat = categoryService.find(category);
        }

        if(cat != null && userId == null) {
            posts = postRepository.findAllByCategoryAndState(pageable, cat, 0);
        } else if(cat == null && userId != null) {
            posts = postRepository.findAllByUserIdAndState(pageable, userId, 0);
        } else if(cat != null && userId != null) {
            posts = postRepository.findAllByCategoryAndUserIdAndState(pageable, cat, userId, 0);
        } else {
            posts = postRepository.findAllByState(0, pageable);
        }

        Page<PostResponse> postResponses = posts.map((new Function<Post, PostResponse>() {
                    @Override
                    public PostResponse apply(Post post) {
                        return getPostResponse(post);
                    }
                }));

        return postResponses;
    }

    @Override
    public Page<PostResponse> findAllFollowingPosts(String username, String category, Pageable pageable) {
        User user = userRepository.findByUsername(username);
        Category cat = null;
        if(category != null) {
            cat = categoryService.find(category);
        }
        if(cat != null)
            return followingRepository.findPostsOfFollowingUsersAndCategory(user, cat, pageable).map(new Function<Post, PostResponse>() {
                @Override
                public PostResponse apply(Post post) {
                    return getPostResponse(post);
                }
            });
        else
            return followingRepository.findPostsOfFollowingUsers(user, pageable).map(new Function<Post, PostResponse>() {
                @Override
                public PostResponse apply(Post post) {
                    return getPostResponse(post);
                }
            });
    }

    private PostResponse getPostResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(post, postResponse);
        // Process CategoryResponse
        postResponse.setCategory(post.getCategory().getCategory());

        // process UserResponse for posts
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(post.getUser(), userResponse);
        userResponse.setPosts(postRepository.countByUser(post.getUser()));
        postResponse.setUser(userResponse);

        // process Comment count
        int commentCount = commentRepository.countByPostAndStateIs(post, 0);
        postResponse.setCommentCount(commentCount);

        // process isUpvoted
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        Upvote upvote = upvoteRepository.findByPostAndUser(post, user);
        boolean isUpvoted = upvote != null;
        postResponse.setUpvoted(isUpvoted);

        // process isDownvoted
        Downvote downvote = downvoteRepository.findByPostAndUser(post, user);
        boolean isDownvoted = downvote != null;
        postResponse.setDownvoted(isDownvoted);
        return postResponse;
    }

    @Override
    public void updateViews(Post post) {
        postRepository.save(post);
    }

    @Override
    public void delete(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        Post post = postRepository.findById(id).get();
        if(user == null) {
            throw  new InsufficientAuthenticationException("Login before delete post!");
        }

        if(post == null) {
            throw  new ResourceNotFoundException("Post doesn't exist!");
        }

        if(user.getUsername().equals(post.getUser().getUsername())) {
            post.setState(-1);
            postRepository.save(post);
        }

    }



    @Override
    public int upvote(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new InsufficientAuthenticationException("Login before upvote");
        }
        Upvote upvote = upvoteRepository.findByPostAndUser(post, user);
        if(upvote != null){
            throw new ApiRequestException("You've already upvoted!");
        }

        upvote = new Upvote(post, user);
        upvoteRepository.save(upvote);
        post.setUpvotes(post.getUpvotes()+1);
        post = postRepository.save(post);
        return post.getUpvotes();
    }

    @Override
    public int unUpvote(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new InsufficientAuthenticationException("Login before unUpvote");
        }
        Upvote upvote = upvoteRepository.findByPostAndUser(post, user);
        if(upvote == null)
            throw new ApiRequestException("You didn't upvote yet.");

        post.setUpvotes(post.getUpvotes()-1);
        upvoteRepository.delete(upvote);
        post = postRepository.save(post);
        return post.getUpvotes();
    }


    @Override
    public int downvote(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new InsufficientAuthenticationException("Login before downvote");
        }
        Downvote downvote = downvoteRepository.findByPostAndUser(post, user);
        if(downvote != null){
            throw new ApiRequestException("You've already downvoted!");
        }

        downvote = new Downvote(post, user);
        downvoteRepository.save(downvote);

        post.setDownvotes(post.getDownvotes()+1);
        post = postRepository.save(post);
        return post.getDownvotes();
    }

    @Override
    public int unDownvote(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new InsufficientAuthenticationException("Login before unDownvote");
        }
        Downvote downvote = downvoteRepository.findByPostAndUser(post, user);
        if(downvote == null){
            throw new ApiRequestException("You didn't downvote yet!");
        }
        downvoteRepository.delete(downvote);
        post.setDownvotes(post.getDownvotes()-1);
        post = postRepository.save(post);
        return post.getDownvotes();
    }
}
