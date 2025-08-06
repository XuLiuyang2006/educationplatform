package com.example.educationplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户DTO")
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
}
