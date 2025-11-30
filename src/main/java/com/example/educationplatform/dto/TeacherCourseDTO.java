package com.example.educationplatform.dto;

import lombok.Data;

@Data
public class TeacherCourseDTO {
    private Long id;       // 课程ID
    private String title;        // 标题
    private String description;  // 描述
    private String contentUrl;   // 视频或文档地址
    private String tags;         // 标签（用逗号分隔）
    private String status;       // 状态（PENDING/ACTIVE/REJECTED/BANNED）
    private Long visitCount;     // 浏览次数
}
