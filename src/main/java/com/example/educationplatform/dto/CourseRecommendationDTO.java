package com.example.educationplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseRecommendationDTO {
    private Long courseId;
    private String title;
    private String reason; // 推荐理由
    private Double score; // 推荐分数
}
