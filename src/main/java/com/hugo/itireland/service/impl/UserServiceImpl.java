package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.Image;
import com.hugo.itireland.domain.User;
import com.hugo.itireland.repository.ImageRepository;
import com.hugo.itireland.repository.PostRepository;
import com.hugo.itireland.repository.UserRepository;
import com.hugo.itireland.s3.S3Buckets;
import com.hugo.itireland.s3.S3Service;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;

    private final S3Service s3Service;
    private final S3Buckets s3Buckets;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(user ->{
            UserResponse userResponse = getUserResponse(user);
            return userResponse;

        });
    }

    private UserResponse getUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);

        // Calculate Level
        int credits = user.getCredits();
        userResponse.setLevel(credits % 20);

        userResponse.setPosts(postRepository.countByUser(user));
        return userResponse;
    }

    @Override
    public UserResponse findById(Long id) {
        return userRepository.findById(id).map(user ->{
//                    UserResponse userResponse = new UserResponse();
//                    BeanUtils.copyProperties(user, userResponse);
//            userResponse.setPosts(postRepository.countByUser(user));
                    return getUserResponse(user);
                }).orElseThrow();
    }

    @Override
    public boolean exist(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String uploadProfileImage(String username, MultipartFile file) throws IOException {
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new InsufficientAuthenticationException("Please sign in first!");
        String imageName = user.getUsername()+"_profle_"+UUID.randomUUID();

        // Upload Image
        s3Service.putObject(
                s3Buckets.getImage(),
                "images/%s/%s".formatted(username, imageName),
                file.getBytes()
        );

        // Save to Database
        Image image = new Image(imageName, user, 1);
        imageRepository.save(image);

        // Update User
        user.setProfileImageName(imageName);
        user.setHeadShotUrl("/images/"+user.getUsername()+"/"+imageName);
        userRepository.save(user);

        return imageName;
    }


}
