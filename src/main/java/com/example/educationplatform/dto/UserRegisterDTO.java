package com.example.educationplatform.dto;

import com.example.educationplatform.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户DTO")
@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String email;
    private RoleEnum role;
}
