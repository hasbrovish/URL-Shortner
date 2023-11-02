package com.assignment.urlshortner.security;

import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;

public class BasicAuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BASIC_PREFIX = "Basic ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(BASIC_PREFIX)) {
            String base64Credentials = authHeader.substring(BASIC_PREFIX.length());
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), "UTF-8");
            String[] values = credentials.split(":", 2);
            if (isValidUser(values[0], values[1])) {
                return true;
            }
        }
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized");
        return false;
    }

    private boolean isValidUser(String username, String password) {
        // Validate the username and password. For simplicity, let's assume "user" and "password" are valid credentials.
        return "user".equals(username) && "password".equals(password);
    }
}
