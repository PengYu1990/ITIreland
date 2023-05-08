package com.hugo.itireland.web.config;


import com.hugo.itireland.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //Login Config
        InterceptorRegistration adminReg = registry.addInterceptor(new LoginInterceptor());
        adminReg.addPathPatterns("/**");
        adminReg.excludePathPatterns("/auth/login");
        adminReg.excludePathPatterns("/auth/signup");

    }
}
