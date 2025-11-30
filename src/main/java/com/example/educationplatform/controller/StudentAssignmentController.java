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

@Tag(name = "学生作业")
@RestController
@RequestMapping("/api/student/assignments")
@RequiredArgsConstructor
public class StudentAssignmentController {

    private final AssignmentService assignmentService;
    private final SubmissionService submissionService;

    @LoginRequired
    @RoleRequired(RoleEnum.STUDENT)
    @Operation(summary = "分页查看我选课的作业")
    @GetMapping
    public Page<AssignmentListDTO> list(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        HttpSession session) {
        Long studentId = (Long) session.getAttribute("userId");
        return assignmentService.listForStudent(studentId, PageRequest.of(page, size));
    }

    @LoginRequired
    @RoleRequired(RoleEnum.STUDENT)
    @Operation(summary = "查看作业详情")
    @GetMapping("/{assignmentId}")
    public Result<AssignmentDetailDTO> detail(@PathVariable Long assignmentId, HttpSession session) {
        Long studentId = (Long) session.getAttribute("userId");
        return Result.success(assignmentService.getDetailForStudent(studentId, assignmentId));
    }

    @LoginRequired
    @RoleRequired(RoleEnum.STUDENT)
    @Operation(summary = "提交作业")
    @PostMapping("/submit")
    public Result<Void> submit(@RequestBody SubmissionCreateDTO dto, HttpSession session) {
        Long studentId = (Long) session.getAttribute("userId");
        submissionService.submit(studentId, dto);
        return Result.success();
    }
    @LoginRequired
    @RoleRequired(RoleEnum.STUDENT)
    @Operation(summary = "更新已提交的作业（截止前）")
    @PutMapping("/submit")
    public Result<Void> updateSubmission(@RequestBody SubmissionCreateDTO dto, HttpSession session) {
        Long studentId = (Long) session.getAttribute("userId");
        submissionService.updateSubmission(studentId, dto);
        return Result.success();
    }

    @LoginRequired
    @RoleRequired(RoleEnum.STUDENT)
    @Operation(summary = "查看我对某作业的提交")
    @GetMapping("/{id}/my-submission")
    public Result<SubmissionListDTO> mySubmission(@PathVariable Long id, HttpSession session) {
        Long studentId = (Long) session.getAttribute("userId");
        return Result.success(submissionService.mySubmission(studentId, id));
    }
}
