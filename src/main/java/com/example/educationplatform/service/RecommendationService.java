package com.example.educationplatform.service;

import com.example.educationplatform.dto.CourseRecommendationDTO;

import java.util.List;

public interface RecommendationService {

    /**
     * 热门推荐
     */
    List<CourseRecommendationDTO> recommendHotCourses();

    /**
     * 个性化推荐
     */
    List<CourseRecommendationDTO> recommendPersonalized(Long studentId);

    /**
     * 相似课程推荐
     */
    List<CourseRecommendationDTO> recommendSimilar(Long courseId);
}
