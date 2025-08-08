package com.example.educationplatform.dto;

import lombok.Data;

@Data
public class StudentCourseDTO {
    private Long courseId;
    private Double progress; // 提交进度
}
