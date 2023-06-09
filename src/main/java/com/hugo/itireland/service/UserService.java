package com.hugo.itireland.service;

import com.hugo.itireland.domain.User;
import com.hugo.itireland.web.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<User> findAll();
    Page<UserResponse> findAll(Pageable pageable);

    UserResponse findById(Long id);

    boolean exist(String username);

    User findByUsername(String username);

    String uploadProfileImage(String username, MultipartFile file) throws IOException;
}
