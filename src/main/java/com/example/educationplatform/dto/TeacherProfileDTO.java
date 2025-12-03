package com.example.educationplatform.dto;

import lombok.Data;

@Data
public class TeacherProfileDTO {
    private Long userId;
    private String expertise;   // 专业领域
    private String tags;        // 课程标签
    private String teachingStyle;// 教学风格
    private Double avgRating;// 学生反馈评分
}
