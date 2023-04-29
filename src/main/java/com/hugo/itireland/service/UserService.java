package com.hugo.itireland.service;

import com.hugo.itireland.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {


    User add(User user);

    List<User> findAll();
    List<User> findAll(Pageable pageable);

    User findById(Long id);
}
