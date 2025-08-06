package com.example.educationplatform.enums;

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(200, "操作成功"),

    //有关用户的状态码
    USER_NOT_FOUND(404, "用户不存在"),
    USERNAME_EXISTS(4001, "用户名已存在"),
    EMAIL_EXISTS(4002, "邮箱已注册"),
    LOGIN_FAILED(4003, "用户名或密码错误"),
    UNAUTHORIZED(401, "未登录"),
    FORBIDDEN(403, "无权限访问"),
    NOT_LOGIN(4011, "未登录，请先登录"),
    EMAIL_NOT_NULl(4004, "邮箱不能为空"),
    PASSWORD_NOT_NULL(4005, "密码不能为空"),
    SERVER_ERROR(500, "服务器异常");


    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
