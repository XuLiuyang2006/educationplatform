package com.example.educationplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "登录DTO")
@Data
public class LoginDTO {
    private String username;
    private String password;
}
