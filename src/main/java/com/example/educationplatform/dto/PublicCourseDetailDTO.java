package com.example.educationplatform.dto;

import com.example.educationplatform.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicCourseDetailDTO {
    private Long courseId;       // 课程 ID
    private String title;        // 课程标题
    private String description;  // 课程简介
    private String tags;         // 课程标签
    private LocalDateTime createdAt;  // 创建时间
    private CourseStatus status;      // 课程状态
    private String contentUrl;   // 教学资源 URL
    private String teacherName;  // 授课教师姓名
    private Long studentCount;   // 选课人数
    private Long visitCount;     // 访问量
}
