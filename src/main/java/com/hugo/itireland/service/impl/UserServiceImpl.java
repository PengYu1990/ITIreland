package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.User;
import com.hugo.itireland.repository.UserRepository;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.dto.response.UserResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(user ->{
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(user, userResponse);
            return userResponse;

        });
    }

    @Override
    public UserResponse findById(Long id) {
        return userRepository.findById(id).map(user ->{
                    UserResponse userResponse = new UserResponse();
                    BeanUtils.copyProperties(user, userResponse);
                    return userResponse;
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


}
