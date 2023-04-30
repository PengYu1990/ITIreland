package com.hugo.itireland.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hugo.itireland.web.util.R;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            ServletContext context = request.getSession().getServletContext();
            String sessionId = request.getParameter("sessionId");

            if(sessionId == null || context.getAttribute(sessionId) == null) {
                R r = R.error(400,"You need to log in");
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(r);
                response.getWriter().write(json);
                return false;
            }

            return true;
        }
    }
