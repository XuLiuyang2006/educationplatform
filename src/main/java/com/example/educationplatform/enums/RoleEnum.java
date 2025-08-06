package com.example.educationplatform.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "用户角色枚举")
public enum RoleEnum {
    STUDENT,
    TEACHER,
    ADMIN
}
