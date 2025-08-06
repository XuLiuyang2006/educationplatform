package com.example.educationplatform.annotation;

import com.example.educationplatform.enums.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE}) // 可以用于方法或类
@Retention(RetentionPolicy.RUNTIME) // 在运行时可用
public @interface RoleRequired {
    RoleEnum[] value();//支持多个角色访问
}
