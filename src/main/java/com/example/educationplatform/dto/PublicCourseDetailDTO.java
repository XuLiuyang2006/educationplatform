package com.example.educationplatform.dto;

import com.example.educationplatform.enums.CourseStatus;

import java.time.LocalDateTime;

public class PublicCourseDetailDTO {
    private Long id;
    private String title;
    private String description;
    private String contentUrl;
    private Long teacherId; // 发布人
    private String teacherName;
    private CourseStatus status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String rejectReason; // 可能为空
}
