package com.example.educationplatform.dto;

import lombok.Data;

@Data
public class StudentProfileDTO {
    //学生id
    private Long userId;
    //学生专业
    private String major;
    //学生年级
    private String grade;
    //学生兴趣方向
    private String interests;
    //学生学习目标
    private String goals;
    //学生历史课程
    private String historyCourses;
    //学生平均学习进度
    private Double avgProgress;
    //学生总学习时长（小时）
    private Integer totalStudyHours;
}
