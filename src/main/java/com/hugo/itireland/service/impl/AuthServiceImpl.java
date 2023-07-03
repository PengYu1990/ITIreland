package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.Role;
import com.hugo.itireland.domain.User;
import com.hugo.itireland.exception.DuplicateResourceException;
import com.hugo.itireland.repository.UserRepository;
import com.hugo.itireland.service.AuthService;
import com.hugo.itireland.web.dto.request.LoginRequest;
import com.hugo.itireland.web.dto.request.RegisterRequest;
import com.hugo.itireland.web.dto.response.AuthResponse;
import com.hugo.itireland.web.security.JwtService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {

        // Validate Register Infomation
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new DuplicateResourceException("Username already has been used!");
        } else if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new DuplicateResourceException("Email already has been used!");
        }


        // Do register
        User user = new User();
        BeanUtils.copyProperties(registerRequest, user);
        user.setCtime(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user = userRepository.save(user);

        AuthResponse authResponse = getAuthResponse(user);

        // Authenticate
        String jwtToken = jwtService.generateToken(user);
        authResponse.setToken(jwtToken);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getUsername(),
                        registerRequest.getPassword()
                )
        );
        return authResponse;
    }

    private static AuthResponse getAuthResponse(User user) {
        // Return Information
        var authResponse = new AuthResponse();
        BeanUtils.copyProperties(user, authResponse);

        //Calculate Lever
        authResponse.setLevel(user.getCredits() % 20);
        return authResponse;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        User user = userRepository.findByUsername(loginRequest.getUsername());
        String jwtToken = jwtService.generateToken(user);
        AuthResponse authResponse = getAuthResponse(user);
        authResponse.setToken(jwtToken);
        return authResponse;
    }

    @Override
    public void logout() {
//        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
    }
}
