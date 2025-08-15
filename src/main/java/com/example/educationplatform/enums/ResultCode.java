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
    SERVER_ERROR(500, "服务器异常"),

    // 有关课程的状态码
    COURSE_NOT_FOUND(4041, "课程不存在"),
    COURSE_CREATION_FAILED(4006, "课程创建失败"),
    ALREADY_ENROLLED(4007, "已选过此课程"),
    NO_PERMISSION(4031, "无权限操作"),
    COURSE_NOT_AVAILABLE(4009, "课程状态不允许选课"),

    //有关教师上传文件的状态码
    FILE_UPLOAD_FAILED(4010, "文件上传失败"),
    FILE_TYPE_NOT_SUPPORTED(4011, "不支持的文件类型"),

    //管理员相关状态码
    INVALID_OPERATION(4008, "无效操作");



    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
