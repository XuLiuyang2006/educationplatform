package com.example.educationplatform.dto;

import com.example.educationplatform.enums.RoleEnum;
import com.example.educationplatform.enums.UserStatus;
import lombok.Data;

@Data
public class UserAdminDTO {
    private Long id; // 用户ID
    private String username; // 用户名
    private RoleEnum role; // 角色（如学生、教师、管理员）
    private UserStatus status; // 状态（如正常、待审核、封禁）
}
