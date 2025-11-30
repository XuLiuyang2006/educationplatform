package com.example.educationplatform.dto;

import com.example.educationplatform.enums.CourseStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminCourseDetailDTO {
    private Long id;              // 课程ID
    private String title;         // 标题
    private String description;   // 简介
    private String contentUrl;    // 视频/文档地址（前端可以预览）
    private String teacherName;   // 教师姓名
    private Long teacherId;       // 教师ID
    private String tags;          // 标签，逗号分隔
    private CourseStatus status;  // 状态（PENDING/APPROVED/REJECTED/OFFLINE）
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private String rejectReason;  // 驳回原因（如果有）
    private Long auditBy;         // 审核人ID
    private LocalDateTime auditTime; // 审核时间
}
