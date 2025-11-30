package com.example.educationplatform.controller;

import com.example.educationplatform.annotation.LoginRequired;
import com.example.educationplatform.annotation.RoleRequired;
import com.example.educationplatform.config.common.Result;
import com.example.educationplatform.dto.*;
import com.example.educationplatform.enums.RoleEnum;
import com.example.educationplatform.service.AssignmentService;
import com.example.educationplatform.service.SubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@Tag(name = "教师作业管理")
@RestController
@RequestMapping("/api/teacher/assignments")
@RequiredArgsConstructor
public class TeacherAssignmentController {

    private final AssignmentService assignmentService;
    private final SubmissionService submissionService;

    @LoginRequired
    @RoleRequired(RoleEnum.TEACHER)
    @Operation(summary = "创建作业")
    @PostMapping
    public Result<Void> create(@RequestBody AssignmentCreateDTO dto, HttpSession session) {
        Long teacherId = (Long) session.getAttribute("userId");
        assignmentService.create(teacherId, dto);
        return Result.success();
    }

    @LoginRequired
    @RoleRequired(RoleEnum.TEACHER)
    @Operation(summary = "更新作业")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody AssignmentCreateDTO dto, HttpSession session) {
        Long teacherId = (Long) session.getAttribute("userId");
        assignmentService.update(teacherId, id, dto);
        return Result.success();
    }

    @LoginRequired
    @RoleRequired(RoleEnum.TEACHER)
    @Operation(summary = "删除作业")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id, HttpSession session) {
        Long teacherId = (Long) session.getAttribute("userId");
        assignmentService.delete(teacherId, id);
        return Result.success();
    }

    @LoginRequired
    @RoleRequired(RoleEnum.TEACHER)
    @Operation(summary = "分页查看某课程的作业")
    @GetMapping("/by-course")
    public Page<AssignmentListDTO> listByCourse(@RequestParam Long courseId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                HttpSession session) {
        Long teacherId = (Long) session.getAttribute("userId");
        return assignmentService.listByCourse(teacherId, courseId, PageRequest.of(page, size));
    }

    @LoginRequired
    @RoleRequired(RoleEnum.TEACHER)
    @Operation(summary = "教师查看作业详情（含统计）")
    @GetMapping("/{id}")
    public Result<AssignmentDetailDTO> detail(@PathVariable Long id, HttpSession session) {
        Long teacherId = (Long) session.getAttribute("userId");
        return Result.success(assignmentService.getDetailForTeacher(teacherId, id));
    }

    // 作业提交列表 & 批改
    @LoginRequired
    @RoleRequired(RoleEnum.TEACHER)
    @Operation(summary = "查看作业提交（分页）")
    @GetMapping("/{id}/submissions")
    public Page<SubmissionListDTO> listSubmissions(@PathVariable Long id,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   HttpSession session) {
        Long teacherId = (Long) session.getAttribute("userId");
        return submissionService.listByAssignmentForTeacher(teacherId, id, PageRequest.of(page, size));
    }

    @LoginRequired
    @RoleRequired(RoleEnum.TEACHER)
    @Operation(summary = "批改作业")
    @PostMapping("/grade")
    public Result<Void> grade(@RequestBody SubmissionGradeDTO dto, HttpSession session) {
        Long teacherId = (Long) session.getAttribute("userId");
        submissionService.grade(teacherId, dto);
        return Result.success();
    }
}
