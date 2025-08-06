package com.example.educationplatform.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE}) // 可以用于方法或类
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
}
