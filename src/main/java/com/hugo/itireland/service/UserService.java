package com.hugo.itireland.service;

import com.hugo.itireland.domain.User;
import com.hugo.itireland.web.dto.request.LoginRequest;
import com.hugo.itireland.web.dto.request.RegisterRequest;
import com.hugo.itireland.web.dto.response.AuthResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<User> findAll();
    List<User> findAll(Pageable pageable);

    User findById(Long id);

    boolean exist(String username);

    User findByUsername(String username);
}
