package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.Role;
import com.hugo.itireland.domain.User;
import com.hugo.itireland.repository.UserRepository;
import com.hugo.itireland.web.dto.request.LoginRequest;
import com.hugo.itireland.web.security.JwtService;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.util.PasswordUtil;
import com.hugo.itireland.web.dto.request.RegisterRequest;
import com.hugo.itireland.web.dto.response.AuthResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).toList();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public boolean exist(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    @Override
    public User login(String username, String password) {
        password = PasswordUtil.md5(password);
        return userRepository.findByUsernameAndPassword(username,password);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
