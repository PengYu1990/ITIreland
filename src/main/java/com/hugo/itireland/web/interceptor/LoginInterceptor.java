package com.hugo.itireland.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hugo.itireland.web.annotation.LoginRequired;
import com.hugo.itireland.web.common.MySessionContext;
import com.hugo.itireland.web.common.R;
import com.hugo.itireland.web.exception.ApiRequestException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

public class LoginInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            if (!(handler instanceof HandlerMethod)) {
                return true;
            }

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.getAnnotation(LoginRequired.class) != null) {
                ServletContext context = request.getSession().getServletContext();
                String sessionId = request.getParameter("sessionId");

                if (sessionId == null || MySessionContext.getSession(sessionId) == null) {
                    throw new ApiRequestException("You need to log in");
                }
            }

            return true;
        }


    }
