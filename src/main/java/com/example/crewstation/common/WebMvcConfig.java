package com.example.crewstation.common;

import com.example.crewstation.auth.JwtTokenProvider;
import com.example.crewstation.interceptor.GuestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GuestInterceptor(jwtTokenProvider))
                .excludePathPatterns(
                        "/",
                        "/api/alarms/**",
                        "/api/auth/logout",
                        "/api/auth/info",
                        "/api/guest/order-detail/**",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/fonts/**",
                        "/guest/order-detail",
                        "/member/login",
                        "/error",
                        "/.well-known/**");


    }
}
