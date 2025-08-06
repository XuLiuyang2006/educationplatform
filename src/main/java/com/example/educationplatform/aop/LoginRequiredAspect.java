package com.example.educationplatform.aop;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoginRequiredAspect {

    private final HttpSession session;//注入 HttpSession，用于获取当前用户的登录状态

    @Before("@annotation(com.example.educationplatform.annotation.LoginRequired)")
    public void checkLogin(){
        //checkLogin()：拦截后执行的方法，如果未登录就抛出异常
        if (session.getAttribute("user") == null) {
            log.warn("用户未登录，拒绝访问");
            throw new RuntimeException("用户未登录，请先登录");
        }
    }
}
