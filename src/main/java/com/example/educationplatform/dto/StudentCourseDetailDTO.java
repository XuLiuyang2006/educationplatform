package com.example.educationplatform.dto;

import com.example.educationplatform.enums.CourseStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCourseDetailDTO {

    private Long id;               // 课程ID
    private String title;          // 课程标题
    private String description;    // 课程简介
    private String contentUrl;     // 视频或文档路径
    private String teacherName;    // 教师姓名
    private Long teacherId;        // 教师ID
    private String tags;           // 标签（逗号分隔）
    private LocalDateTime createTime; // 发布时间
    private Long visitCount;       // 浏览量
    private Long studentCount;     // 已选课人数
    private CourseStatus status;   // 审核状态（对学生来说通常只会是 APPROVED）
}
