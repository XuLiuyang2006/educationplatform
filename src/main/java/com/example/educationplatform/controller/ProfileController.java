package com.example.educationplatform.controller;

import com.example.educationplatform.annotation.LoginRequired;
import com.example.educationplatform.annotation.RoleRequired;
import com.example.educationplatform.dto.StudentProfileDTO;
import com.example.educationplatform.dto.TeacherProfileDTO;
import com.example.educationplatform.enums.RoleEnum;
import com.example.educationplatform.service.ProfileService;
import com.example.educationplatform.config.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ProfileController", description = "用户档案相关接口")
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

//    @Operation(summary = "获取学生档案", description = "根据用户ID获取学生档案")
//    // === 学生档案 ===
//    @GetMapping("/student/{userId}")
//    public Result<StudentProfileDTO> getStudentProfile(@PathVariable Long userId) {
//        return Result.success(profileService.getStudentProfile(userId));
//    }

    @RoleRequired(RoleEnum.STUDENT)
    @Operation(summary = "更新学生档案", description = "更新学生档案信息")
    @PutMapping("/student")
    public Result<StudentProfileDTO> updateStudentProfile(@RequestBody StudentProfileDTO dto,
                                                          HttpSession session) {
        Long userId = (Long)  session.getAttribute("userId");
        return Result.success(profileService.updateStudentProfile(dto, userId));
    }

    @Operation(summary = "获取学生自己的档案",description = "获取登录学生自己的档案")
    @LoginRequired
    @RoleRequired(RoleEnum.STUDENT)
    @GetMapping("/student/me")
    public Result<StudentProfileDTO> getMyStudentProfile(HttpSession session){
        Long userId = (Long) session.getAttribute("userId");
        return Result.success(profileService.getStudentProfile(userId));
    }

    @Operation(summary = "获取教师自己的档案",description = "获取登录教师自己的档案")
    @LoginRequired
    @RoleRequired(RoleEnum.TEACHER)
    @GetMapping("/teacher/me")
    public Result<TeacherProfileDTO> getMyTeacherProfile(HttpSession session){
        Long userId = (Long) session.getAttribute("userId");
        return Result.success(profileService.getTeacherProfile(userId));
    }

//    @Operation(summary = "获取教师档案", description = "根据用户ID获取教师档案")
//    // === 教师档案 ===
//    @GetMapping("/teacher/{userId}")
//    public Result<TeacherProfileDTO> getTeacherProfile(@PathVariable Long userId) {
//        return Result.success(profileService.getTeacherProfile(userId));
//    }

    @LoginRequired
    @RoleRequired(RoleEnum.TEACHER)
    @Operation(summary = "更新教师档案", description = "更新教师档案信息")
    @PutMapping("/teacher")
    public Result<TeacherProfileDTO> updateTeacherProfile(@RequestBody TeacherProfileDTO dto,
                                                          HttpSession session) {
        Long userId = (Long)  session.getAttribute("userId");
        return Result.success(profileService.updateTeacherProfile(dto, userId));
    }
}
