package com.example.educationplatform.controller;

import com.example.educationplatform.annotation.LoginRequired;
import com.example.educationplatform.annotation.RoleRequired;
import com.example.educationplatform.common.Result;
import com.example.educationplatform.dto.TeacherCourseCreateDTO;
import com.example.educationplatform.dto.StudentCourseListDTO;
import com.example.educationplatform.dto.TeacherCourseListDTO;
import com.example.educationplatform.enums.RoleEnum;
import com.example.educationplatform.service.TeacherCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "教师课程管理", description = "教师创建、更新、删除和查询课程接口")
@RestController
@RequestMapping("/api/teacher/course")
@RequiredArgsConstructor
public class TeacherCourseController {

    private final TeacherCourseService teacherCourseService;

    @LoginRequired // 确保教师登录后才能访问这些接口
    @Operation(summary = "创建课程", description = "教师创建新课程接口")
    @RoleRequired(RoleEnum.TEACHER)
    @PostMapping
    public Result<Void> createCourse(@RequestBody TeacherCourseCreateDTO dto, HttpSession session) {
        Long teacherId = (Long) session.getAttribute("userId");
        teacherCourseService.createCourse(teacherId, dto);
        return Result.success();
    }

    @LoginRequired // 确保教师登录后才能访问这些接口
    @Operation(summary = "更新课程", description = "教师更新课程信息接口")
    @RoleRequired(RoleEnum.TEACHER)
    @PutMapping("/{id}")
    public Result<Void> updateCourse(@PathVariable Long id, @RequestBody TeacherCourseCreateDTO dto, HttpSession session) {
        Long teacherId = (Long) session.getAttribute("userId");
        teacherCourseService.updateCourse(id, teacherId, dto);
        return Result.success();
    }

    @LoginRequired // 确保教师登录后才能访问这些接口
    @Operation(summary = "删除课程", description = "教师删除课程接口")
    @RoleRequired(RoleEnum.TEACHER)
    @DeleteMapping("/{id}")
    public Result<Void> deleteCourse(@PathVariable Long id, HttpSession session) {
        Long teacherId = (Long) session.getAttribute("userId");
        teacherCourseService.deleteCourse(id, teacherId);
        return Result.success();
    }

    @LoginRequired // 确保教师登录后才能访问这些接口
    @Operation(summary = "获取我的课程列表", description = "教师获取自己创建的课程列表接口")
    @RoleRequired(RoleEnum.TEACHER)
    @GetMapping("/my")
    public Result<List<TeacherCourseListDTO>> listMyCourses(HttpSession session) {
        Long teacherId = (Long) session.getAttribute("userId");
        List<TeacherCourseListDTO> list = teacherCourseService.listMyCoursesList(teacherId);
        return Result.success(list);
    }
}
