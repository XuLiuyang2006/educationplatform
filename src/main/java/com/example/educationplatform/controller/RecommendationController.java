package com.example.educationplatform.controller;

import com.example.educationplatform.config.common.Result;
import com.example.educationplatform.dto.CourseRecommendationDTO;
import com.example.educationplatform.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "推荐系统接口")
@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Operation(summary = "获取热门推荐课程", description = "返回系统中最新发布的热门课程")
    @GetMapping("/hot")
    public Result<List<CourseRecommendationDTO>> getHotCourses() {
        return Result.success(recommendationService.recommendHotCourses());
    }

    @Operation(summary = "获取个性化推荐课程", description = "根据学生的兴趣和专业，返回个性化推荐课程")
    @GetMapping("/personalized")
    public Result<List<CourseRecommendationDTO>> getPersonalizedCourses(HttpSession session) {
        Long studentId = (Long) session.getAttribute("userId");
        return Result.success(recommendationService.recommendPersonalized(studentId));
    }

    @Operation(summary = "获取相似课程推荐", description = "根据指定课程，返回相似的课程推荐")
    @GetMapping("/similar/{courseId}")
    public Result<List<CourseRecommendationDTO>> getSimilarCourses(@PathVariable Long courseId) {
        return Result.success(recommendationService.recommendSimilar(courseId));
    }
}
