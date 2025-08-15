package com.example.educationplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseListDTO {
    private Long id;
    private String title; // 课程标题
    private String contentUrl; // 视频或文档路径
}
