package com.hugo.itireland.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorting).descending());

        Page<PostResponse> postResponses = postService.findAll(pageable, category);


        return R.success(postResponses.stream().toList(), postResponses.getTotalPages(),
                postResponses.getTotalElements(),
                postResponses.getPageable().getPageNumber());

    }


    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id){
        postService.delete(id);
        return R.success(null);
    }
}
