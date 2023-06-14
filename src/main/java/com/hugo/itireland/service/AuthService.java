package com.hugo.itireland.service;

import com.hugo.itireland.web.dto.request.LoginRequest;
import com.hugo.itireland.web.dto.request.RegisterRequest;
import com.hugo.itireland.web.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
}
