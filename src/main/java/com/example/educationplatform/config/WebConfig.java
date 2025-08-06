package com.example.educationplatform.config;

import com.example.educationplatform.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns(
                        "/api/users/login",
                        "/api/users/register",
                        "/swagger-ui/**", // 放行swagger的静态资源
                        "/v3/api-docs/**", // 放行文档接口
                        "/swagger-resources/**", // 如果用了旧版本swagger
                        "/webjars/**", // swagger静态资源
                        "/error"); // 放行登录注册
    }
}
