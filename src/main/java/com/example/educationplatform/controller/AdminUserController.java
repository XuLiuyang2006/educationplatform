package com.example.educationplatform.controller;


import com.example.educationplatform.annotation.LoginRequired;
import com.example.educationplatform.annotation.RoleRequired;
import com.example.educationplatform.dto.UserAdminDTO;
import com.example.educationplatform.enums.RoleEnum;
import com.example.educationplatform.enums.UserStatus;
import com.example.educationplatform.service.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "管理员用户管理接口", description = "提供管理员对用户的管理功能，包括获取、审核、封禁等操作")
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    //GET的代码
    @GetMapping
    @Operation(summary = "获取所有用户信息", description = "支持按角色和状态过滤")
    @LoginRequired
    @RoleRequired(RoleEnum.ADMIN) // 确保只有管理员可以访问此接口
    public List<UserAdminDTO> getAllUsers(
        @RequestParam(required = false) RoleEnum role,
        @RequestParam(required = false) UserStatus status ){
        return adminUserService.getAllUsers(role, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取用户信息", description = "获取指定ID的用户信息")
    @LoginRequired
    @RoleRequired(RoleEnum.ADMIN) // 确保只有管理员可以访问此接口
    public UserAdminDTO getUserById(@PathVariable Long id) {
        return adminUserService.getUserById(id);
    }

    // PUT的代码
    @PutMapping("/{id}/approve")
    @Operation(summary = "审核通过用户", description = "将指定ID的用户状态设置为ACTIVE")
    @LoginRequired
    @RoleRequired(RoleEnum.ADMIN) // 确保只有管理员可以访问此接口
    public void approveUser(@PathVariable Long id) {
        adminUserService.approveUser(id);
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "审核拒绝用户", description = "将指定ID的用户状态设置为REJECTED")
    @LoginRequired
    @RoleRequired(RoleEnum.ADMIN) // 确保只有管理员可以访问此接口
    public void rejectUser(@PathVariable Long id) {
        adminUserService.rejectUser(id);
    }

    @PutMapping("/{id}/ban")
    @Operation(summary = "封禁用户", description = "将指定ID的用户状态设置为BANNED")
    @LoginRequired
    @RoleRequired(RoleEnum.ADMIN) // 确保只有管理员可以访问此接口
    public void banUser(@PathVariable Long id) {
        adminUserService.banUser(id);
    }

    @PutMapping("/{id}/unban")
    @Operation(summary = "解封用户", description = "将指定ID的用户状态设置为ACTIVE")
    @LoginRequired
    @RoleRequired(RoleEnum.ADMIN) // 确保只有管理员可以访问此接口
    public void unbanUser(@PathVariable Long id) {
        adminUserService.unbanUser(id);
    }

    // DELETE的代码
    @DeleteMapping("/{id}/delete")
    @Operation(summary = "删除用户", description = "删除指定ID的用户")
    @LoginRequired
    @RoleRequired(RoleEnum.ADMIN) // 确保只有管理员可以访问此接口
    public void deleteUser(@PathVariable Long id) {
        adminUserService.deleteUser(id);
    }

}
