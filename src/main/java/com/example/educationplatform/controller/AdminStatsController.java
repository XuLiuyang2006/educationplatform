package com.example.educationplatform.controller;

import com.example.educationplatform.annotation.LoginRequired;
import com.example.educationplatform.annotation.RoleRequired;
import com.example.educationplatform.common.Result;
import com.example.educationplatform.enums.RoleEnum;
import com.example.educationplatform.service.AdminStatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

//TODO：这个一点没看懂
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/stats")
@Tag(name = "管理员统计", description = "管理员查看平台各项统计数据接口")
public class AdminStatsController {

    private final AdminStatsService adminStatsService;

    //GET的代码

    @Operation(summary = "获取平台概览数据", description = "包括课程总数、学生总数、教师总数")
    @LoginRequired
    @RoleRequired(RoleEnum.ADMIN)
    @GetMapping("/overview")
    public Result<Map<String, Long>> getOverview() {
            Map<String, Long> data = new HashMap<>();
            data.put("courseCount", adminStatsService.getCourseCount());
            data.put("studentCount", adminStatsService.getStudentCount());
            data.put("teacherCount", adminStatsService.getTeacherCount());
            return Result.success(data);
    }

    @Operation(summary = "获取新增用户和课程数据", description = "按日期范围统计新增用户和课程数量")
    @LoginRequired
    @RoleRequired(RoleEnum.ADMIN)
    @GetMapping("/new-data")
    public Result<Map<String, Long>> getNewData(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        Map<String, Long> data = new HashMap<>();
        data.put("newUserCount", adminStatsService.getNewUserCount(start, end));
        data.put("newCourseCount", adminStatsService.getNewCourseCount(start, end));
        return Result.success(data);
    }

    @Operation(summary = "获取平台活动数据", description = "包括选课次数和访问次数")
    @LoginRequired
    @RoleRequired(RoleEnum.ADMIN)
    @GetMapping("/activity")
    public Result<Map<String, Long>> getActivity(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        Map<String, Long> data = new HashMap<>();
        data.put("courseSelectionCount", adminStatsService.getCourseSelectionCount(start, end));
        data.put("visitCount", adminStatsService.getVisitCount(start, end));
        return Result.success(data);
    }

}

