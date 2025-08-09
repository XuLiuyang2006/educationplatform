package com.example.educationplatform.dto;

import com.example.educationplatform.enums.CourseStatus;
import lombok.Data;

@Data
public class AdminCourseAuditDTO {
    private Long courseId; // 课程ID
    private CourseStatus status; // 审核状态
    private String reason; // 审核理由。可选，拒绝原因
}
