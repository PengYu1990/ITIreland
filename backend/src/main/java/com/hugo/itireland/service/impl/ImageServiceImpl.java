package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.Image;
import com.hugo.itireland.domain.User;
import com.hugo.itireland.repository.ImageRepository;
import com.hugo.itireland.repository.UserRepository;
import com.hugo.itireland.s3.S3Buckets;
import com.hugo.itireland.s3.S3Service;
import com.hugo.itireland.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final S3Service s3Service;
    private final S3Buckets s3Buckets;

    private final ImageRepository imageRepository;

    private final UserRepository userRepository;
    @Override
    public String upload(String username, MultipartFile file, int type) throws IOException {
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new InsufficientAuthenticationException("You need to login first!");
        String imageName = UUID.randomUUID().toString();
        s3Service.putObject(
                s3Buckets.getImage(),
                "images/%s/%s".formatted(username, imageName),
                file.getBytes()
        );
        Image image = new Image(imageName, user,type);
        imageRepository.save(image);

        return imageName;

    }

    @Override
    public byte[] get(String username, String imageName) {
        return s3Service.getObject(
                s3Buckets.getImage(),
                "images/%s/%s".formatted(username, imageName)
        );
    }
}
