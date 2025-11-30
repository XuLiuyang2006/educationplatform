package com.example.educationplatform.dto;

import lombok.Data;

@Data
public class StudentProfileDTO {
    private Long userId;
    private String major;
    private String grade;
    private String interests;
    private String goals;
    private String historyCourses;
    private Double avgProgress;
    private Integer totalStudyHours;
}
