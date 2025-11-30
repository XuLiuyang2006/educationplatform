package com.example.educationplatform.dto;

import lombok.Data;

@Data
public class TeacherProfileDTO {
    private Long userId;
    private String expertise;
    private String tags;
    private String teachingStyle;
    private Double avgRating;
}
