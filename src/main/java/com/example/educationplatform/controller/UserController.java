package com.example.educationplatform.controller;

import com.example.educationplatform.annotation.LoginRequired;
import com.example.educationplatform.annotation.RoleRequired;
import com.example.educationplatform.common.Result;
import com.example.educationplatform.dto.LoginDTO;
import com.example.educationplatform.dto.UserDTO;
import com.example.educationplatform.entity.User;
import com.example.educationplatform.enums.RoleEnum;
import com.example.educationplatform.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "用户管理", description = "用户注册、登录及信息获取接口")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //POST的代码
    @Operation(summary = "用户注册", description = "用户注册接口")
    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody User user) {
        userService.register(user);
        return Result.success();
    }

    @Operation(summary = "用户登录", description = "用户登录接口")
    @PostMapping("/login")
    public Result<UserDTO> login(@RequestBody LoginDTO loginDTO) {
        UserDTO dto = userService.login(loginDTO);
        return Result.success(dto);
    }

    @LoginRequired  // 使用自定义注解，确保用户已登录
    @Operation(summary = "用户登出", description = "用户登出接口")
    @PostMapping("/logout")
    public Result<Void> logout(HttpSession session) {
        session.invalidate();  // 销毁当前 Session
        return Result.success();
    }

    //GET的代码
    @LoginRequired  // 使用自定义注解，确保用户已登录
    @RoleRequired(RoleEnum.ADMIN)  // 使用自定义注解，确保用户是管理员
    @Operation(summary = "获取用户信息", description = "根据ID获取用户信息接口")
    @GetMapping("/{id}")
    public Result<UserDTO> getById(@PathVariable Long id) {
        UserDTO dto = userService.getById(id);
        return Result.success(dto);
    }

    @Operation(summary = "获取当前登录用户信息", description = "获取当前登录用户信息接口")
    @GetMapping("/admin-only")
    @LoginRequired
    @RoleRequired({RoleEnum.ADMIN})
    public Result<String> adminOnly() {
        return Result.success("只有管理员可以访问这个接口");
    }

    @Operation(summary = "获取教师或管理员可访问的接口", description = "教师或管理员可访问的接口")
    @GetMapping("/teacher-or-admin")
    @LoginRequired
    @RoleRequired({RoleEnum.TEACHER, RoleEnum.ADMIN})
    public Result<String> teacherAndAdminCanAccess() {
        return Result.success("教师或管理员可访问");
    }

    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户信息接口")
    @GetMapping("/me")
    @LoginRequired
    public Result<UserDTO> getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        UserDTO userDTO = userService.getById(userId);
        return Result.success(userDTO);
    }


    //PUT的代码
    @LoginRequired  // 使用自定义注解，确保用户已登录
    @Operation(summary = "更新用户信息", description = "更新用户信息接口")
    @PutMapping("/update")
    public Result<Void> update(@Valid @RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    //

}
