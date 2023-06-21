package com.hugo.itireland.web.controller;


import com.hugo.itireland.service.ImageService;
import com.hugo.itireland.web.common.R;
import com.hugo.itireland.web.dto.response.ImageUploadResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageService imageService;

    @PostMapping(value = "post-image-upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R postImageUpload(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String imageName = imageService.upload(username, file,0);
        String url = request.getContextPath() + "/images/%s/%s".formatted(username, imageName);
        return R.success(new ImageUploadResponse(username, imageName, url));
    }

    @GetMapping(
            value = "{username}/{imageName}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getPostImage(
            @PathVariable("username") String username,
            @PathVariable("imageName") String imageName) {
        return imageService.get(username, imageName);
    }
}
