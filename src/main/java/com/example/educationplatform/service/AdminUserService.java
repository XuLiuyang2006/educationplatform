package com.example.educationplatform.service;

import com.example.educationplatform.dto.UserAdminDTO;
import com.example.educationplatform.enums.RoleEnum;
import com.example.educationplatform.enums.UserStatus;

import java.util.List;

public interface AdminUserService {
    List<UserAdminDTO> getAllUsers(RoleEnum role, UserStatus status);// 获取所有用户，支持按角色和状态过滤
    UserAdminDTO getUserById(Long id);// 根据ID获取用户信息
    void approveUser(Long id);// 审核通过用户
    void rejectUser(Long id);// 审核拒绝用户
    void banUser(Long id);// 封禁用户
    void unbanUser(Long id);// 解封用户
    void deleteUser(Long id);// 删除用户
}
