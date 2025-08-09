package com.example.educationplatform.controller;

import com.example.educationplatform.common.Result;
import com.example.educationplatform.dto.AdminCourseAuditDTO;
import com.example.educationplatform.entity.Course;
import com.example.educationplatform.service.AdminCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "管理员课程管理接口", description = "管理员对课程的审核和管理功能")
@RestController
@RequestMapping("/api/admin/courses")
@RequiredArgsConstructor
public class AdminCourseController {

    private final AdminCourseService adminCourseService; // 假设有一个课程服务类处理业务逻辑

    //GET代码

    // 查询待审核课表
    @Operation(summary = "查询待审核课表", description = "获取所有状态为待审核的课程列表")
    @GetMapping("/pending")
    public Result<List<Course>> getPendingCourses() {
        return Result.success(adminCourseService.getPendingCourses());
    }

    // 查询所有课表
    @Operation(summary = "查询所有课表", description = "获取所有课程列表，包括已审核和待审核的课程")
    @GetMapping("/all")
    public Result<List<Course>> getAllCourses() {
        return Result.success(adminCourseService.getAllCourses());
    }

    // POST代码

    //审核课程
    //TODO:这里的adminId是从请求头中获取的，前端需要在请求头中添加adminId，不明白为什么不是从session中获取
    @Operation(summary = "审核课程", description = "管理员审核课程，更新课程状态为通过或拒绝")
    @PostMapping("/audit")
    public Result<Void> auditCourse(@RequestBody AdminCourseAuditDTO dto,
                                    HttpSession session) {
        Long adminId = (Long) session.getAttribute("userId"); // 从session中获取管理员ID
        adminCourseService.auditCourse(dto, adminId);
        return Result.success();
    }

    // 下架课程
    @Operation(summary = "下架课程", description = "管理员将课程下架，课程状态更新为已下架")
    @PostMapping("/{courseId}/offline")
    public Result<Void> offlineCourse(@PathVariable Long courseId,
                                      @RequestAttribute("adminId") Long adminId) {
        adminCourseService.offlineCourse(courseId, adminId);
        return Result.success();
    }
}
