package com.example.educationplatform.dto;

import com.example.educationplatform.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminCourseDTO {

    private Long id;                  // 课程ID
    private String title;             // 课程标题
    private String description;       // 课程描述
    private String teacherName;       // 教师姓名
    private Long teacherId;           // 教师ID
    private CourseStatus status;      // 课程状态
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private Long auditBy;             // 审核人ID
    private LocalDateTime auditTime;  // 审核时间
    private String rejectReason;      // 拒绝原因
    private Long visitCount;          // 访问量
    private String tags;              // 课程标签
}
