package com.example.crewstation.interceptor;

import com.example.crewstation.auth.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class GuestInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;


    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

        String username = null;

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                    break;
                }
            }
        }

        if (username != null) {
            jwtTokenProvider.deleteRefreshToken(username);
            res.sendRedirect("/member/login");
            return false;
        }

        return true;
    }

}
