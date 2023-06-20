package com.hugo.itireland.web.controller;


import com.hugo.itireland.exception.ValidationException;
import com.hugo.itireland.service.CommentService;
import com.hugo.itireland.web.common.R;
import com.hugo.itireland.web.dto.request.CommentRequest;
import com.hugo.itireland.web.dto.response.CommentResponse;
import com.hugo.itireland.web.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final JwtService jwtService;


    @PostMapping
    public R save(@Validated @RequestBody CommentRequest commentRequest, BindingResult errors){
        //Throw Validation Exception
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
        return R.success(commentService.add(commentRequest));
    }


    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id, @RequestHeader String Authorization){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        commentService.delete(id, authentication.getName());
        return R.success(null);
    }

    @GetMapping("/{id}")
    public R get(@PathVariable Long id){

        return R.success(commentService.findById(id));
    }


    @GetMapping
    public R find(@RequestParam(defaultValue = "0", required = false) Integer page,
                  @RequestParam(defaultValue = "100", required = false) Integer size,
                  @RequestParam(defaultValue = "id", required = false) String sort,
                  @RequestParam Long postId
                  ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        List<CommentResponse> comments = commentService.findAllByPostId(pageable, postId);
        return R.success(comments);

    }
}
