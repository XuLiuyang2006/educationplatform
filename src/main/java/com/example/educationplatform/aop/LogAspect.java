package com.example.educationplatform.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class LogAspect {
    @Pointcut("execution(* com.example.educationplatform.controller.*.*(..))")
    public void allControllerMethods(){}

    @Before("allControllerMethods()")
    public void logAllControllerMethods(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();

//        //跳过UserController类
//        if (className.contains("UserController")) {
//            return; // 如果是 UserController 类，则不记录日志
//        }

        log.info("🟢 调用方法: {},类名:{}", method, className);
    }
}

