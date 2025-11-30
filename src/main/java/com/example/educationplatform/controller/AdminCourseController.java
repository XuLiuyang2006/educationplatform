package com.example.educationplatform.controller;

import com.example.educationplatform.annotation.LoginRequired;
import com.example.educationplatform.config.common.Result;
import com.example.educationplatform.dto.AdminCourseAuditDTO;
import com.example.educationplatform.dto.AdminCourseDTO;
import com.example.educationplatform.dto.AdminCourseDetailDTO;
import com.example.educationplatform.enums.CourseStatus;
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
    @LoginRequired
    @Operation(summary = "查询待审核课表", description = "获取所有状态为待审核的课程列表")
    @GetMapping("/pending")
    public Result<List<AdminCourseDTO>> getPendingCourses() {
        return Result.success(adminCourseService.getPendingCourses());
    }

    // 查询所有课表
    @LoginRequired
    @Operation(summary = "查询所有课表", description = "获取所有课程列表，包括已审核和待审核的课程")
    @GetMapping("/all")
    public Result<List<AdminCourseDTO>> getAllCourses() {
        return Result.success(adminCourseService.getAllCourses());
    }

    // 查询单个课程详情（给审核用）
    @LoginRequired
    @Operation(summary = "查询课程详情", description = "管理员查看单个课程的详细信息")
    @GetMapping("/{courseId}")
    public Result<AdminCourseDetailDTO> getCourseDetail(@PathVariable Long courseId) {
        return Result.success(adminCourseService.getCourseDetail(courseId));
    }



    // POST代码

//    //审核课程
//    @LoginRequired
//    //TODO:这里的adminId是从请求头中获取的，前端需要在请求头中添加adminId，不明白为什么不是从session中获取
//    @Operation(summary = "审核课程", description = "管理员审核课程，更新课程状态为通过或拒绝")
//    @PostMapping("/audit")
//    public Result<Void> auditCourse(@RequestBody AdminCourseAuditDTO dto,
//                                    HttpSession session) {
//        Long adminId = (Long) session.getAttribute("userId"); // 从session中获取管理员ID
//        adminCourseService.auditCourse(dto, adminId);
//        return Result.success();
//    }

    //通过课程
    @LoginRequired
    @Operation(summary = "通过课程", description = "管理员通过课程，更新课程状态为已通过")
    @PostMapping("/{courseId}/approve")
    public Result<Void> approveCourse(@PathVariable Long courseId,
                                      HttpSession session) {
        Long adminId = (Long) session.getAttribute("userId"); // 从session中获取管理员ID
        AdminCourseAuditDTO dto = new AdminCourseAuditDTO();
        dto.setCourseId(courseId);
        dto.setStatus(CourseStatus.APPROVED);
        adminCourseService.auditCourse(dto, adminId);
        return Result.success();
    }

    //拒绝课程
    @LoginRequired
    @Operation(summary = "拒绝课程", description = "管理员拒绝课程，更新课程状态为拒绝并提供拒绝理由")
    @PostMapping("/{courseId}/reject")
    public Result<Void> rejectCourse(@PathVariable Long courseId,
                                    @RequestParam String rejectReason,
                                    HttpSession session) {
        Long adminId = (Long) session.getAttribute("userId"); // 从session中获取管理员ID
        AdminCourseAuditDTO dto = new AdminCourseAuditDTO();
        dto.setCourseId(courseId);
        dto.setStatus(CourseStatus.REJECTED);
        dto.setReason(rejectReason);
        adminCourseService.auditCourse(dto, adminId);
        return Result.success();
    }

    // 下架课程
    @LoginRequired
    @Operation(summary = "下架课程", description = "管理员将课程下架，课程状态更新为已下架")
    @PostMapping("/{courseId}/offline")
    public Result<Void> offlineCourse(@PathVariable Long courseId,
                                      HttpSession session) {

        Long adminId = (Long) session.getAttribute("userId"); // 从session中获取管理员ID
        adminCourseService.offlineCourse(courseId, adminId);
        return Result.success();
    }
}
