package com.hugo.itireland.web.controller;

import com.hugo.itireland.domain.User;
import com.hugo.itireland.s3.S3Buckets;
import com.hugo.itireland.service.ImageService;
import com.hugo.itireland.web.dto.response.ImageUploadResponse;
import com.hugo.itireland.web.dto.response.UserResponse;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.common.R;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ImageService imageService;


    @GetMapping
    public R findAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                              @RequestParam(defaultValue = "20", required = false) Integer size,
                              @RequestParam(defaultValue = "id", required = false) String sort
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<UserResponse> userResponses =  userService.findAll(pageable);
        return R.success(userResponses, userResponses.getTotalPages(),
                userResponses.getTotalElements(),
                userResponses.getPageable().getPageNumber());

    }


    @GetMapping("/{id}")
    public R findById(@PathVariable Long id){
        return R.success(userService.findById(id));
    }


    @PostMapping(value = "profile-image-upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R profileImageUpload(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String imageName = userService.uploadProfileImage(username, file);
        String url = request.getContextPath() + "/images/%s/%s".formatted(username, imageName);
        return R.success(new ImageUploadResponse(username, imageName, url));
    }

}
