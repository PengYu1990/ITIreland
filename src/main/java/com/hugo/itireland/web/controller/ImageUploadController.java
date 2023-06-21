package com.hugo.itireland.web.controller;


import com.hugo.itireland.s3.S3Buckets;
import com.hugo.itireland.s3.S3Service;
import com.hugo.itireland.service.ImageService;
import com.hugo.itireland.web.common.R;
import com.hugo.itireland.web.dto.response.ImageUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageService imageService;


    @PostMapping(value = "post-image-upload/{userId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R postImageUpload(@PathVariable("userId") Long userId,
                         @RequestParam("file") MultipartFile file) throws IOException {
        String imageName = imageService.upload(userId, file,0);
        return R.success(new ImageUploadResponse(imageName));
    }

//    @PostMapping(value = "post-image-upload/{userId}",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public R profileImageUpload(@PathVariable("userId") Long userId,
//                         @RequestParam("file") MultipartFile file) throws IOException {
//        imageService.upload(userId, file,0);
//        return R.success(null);
//    }

    @GetMapping(
            value = "image/{userId}/{imageName}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getCustomerProfileImage(
            @PathVariable("userId") Long userId,
            @PathVariable("imageName") String imageName) {
        return imageService.get(userId, imageName);
    }
}
