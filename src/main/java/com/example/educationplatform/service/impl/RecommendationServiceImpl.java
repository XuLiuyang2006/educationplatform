package com.example.educationplatform.service.impl;

import com.example.educationplatform.dto.AiChatDTO;
import com.example.educationplatform.dto.CourseRecommendationDTO;
import com.example.educationplatform.entity.Course;
import com.example.educationplatform.entity.StudentProfile;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.exception.BizException;
import com.example.educationplatform.repository.CourseRepository;
import com.example.educationplatform.repository.StudentCourseRepository;
import com.example.educationplatform.repository.StudentProfileRepository;
import com.example.educationplatform.service.AiChatService;
import com.example.educationplatform.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.educationplatform.enums.CourseStatus;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final CourseRepository courseRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final AiChatService aiChatService;

    /**
     * 热门推荐（这里用最新课程代替，可以扩展成访问量统计）
     */
    @Override
    public List<CourseRecommendationDTO> recommendHotCourses() {
        List<Course> allCourses = courseRepository.findByStatus(CourseStatus.APPROVED);

        List<CourseRecommendationDTO> result = new ArrayList<>();
        Set<Long> seen = new HashSet<>();

        for (Course course : allCourses) {
            Long selectionCount = studentCourseRepository.countByCourseId(course.getId());
            Long visitCount = course.getVisitCount();

            double score = 0.7 * selectionCount + 0.3 * visitCount;

            if (seen.size() < 10) {
                seen.add(course.getId());
                String reason = generateAiReason(null,
                        "请用一句简短的话告诉用户，为什么推荐课程《" + course.getTitle() + "》");

                result.add(new CourseRecommendationDTO(
                        course.getId(),
                        course.getTitle(),
                        reason != null ? reason : "🔥 热门课程推荐",
                        score
                ));
            }
        }

        result.sort((r1, r2) -> Double.compare(r2.getScore(), r1.getScore()));
        return result.subList(0, Math.min(result.size(), 10));
    }




    /**
     * 个性化推荐（根据学生兴趣 + 课程 tags 匹配）
     */
    @Override
    public List<CourseRecommendationDTO> recommendPersonalized(Long studentId) {
        StudentProfile profile = studentProfileRepository.findById(studentId)
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND, "学生信息不存在"));

        String interests = profile.getInterests();
        if (interests == null || interests.isBlank()) {
            throw new BizException(ResultCode.BAD_REQUEST, "请先完善兴趣信息，才能获取个性化推荐");
        }

        List<CourseRecommendationDTO> result = new ArrayList<>();
        Set<Long> seen = new HashSet<>();

        String[] keywords = interests.split(",");
        for (String keyword : keywords) {
            String kw = keyword.trim();
            if (kw.isEmpty()) continue;

            List<Course> courses = courseRepository.findByTagsContainingIgnoreCase(kw);

            for (Course c : courses) {
                if (seen.add(c.getId())) {
                    String reason = generateAiReason(studentId,
                            "用户对【" + kw + "】感兴趣，请用一句简短的话说明为什么推荐课程《" + c.getTitle() + "》");

                    // 个性化推荐的 score 可以简单定义成 50 + 随机分数，或者 1.0 固定值
                    result.add(new CourseRecommendationDTO(
                            c.getId(),
                            c.getTitle(),
                            reason != null ? reason : "🎯 因为你对【" + kw + "】感兴趣",
                            50.0
                    ));
                }
            }
        }
        return result;
    }

    /**
     * 相似课程推荐（根据课程标题关键字找相似课程）
     */
    @Override
    public List<CourseRecommendationDTO> recommendSimilar(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "课程不存在"));

        if (course.getTags() == null || course.getTags().isBlank()) {
            return List.of();
        }

        String[] tags = course.getTags().split(",");
        Set<Long> seen = new HashSet<>();
        List<CourseRecommendationDTO> result = new ArrayList<>();

        for (String tag : tags) {
            List<Course> similar = courseRepository.findByTag(course.getId(), tag.trim());
            for (Course c : similar) {
                if (seen.add(c.getId())) {
                    String reason = "📌 推荐理由：和《" + course.getTitle() + "》同属【" + tag.trim() + "】方向";
                    result.add(new CourseRecommendationDTO(
                            c.getId(),
                            c.getTitle(),
                            reason,
                            30.0 // 相似课程的分数你可以自己定义，比如 30
                    ));
                }
            }
        }
        return result;
    }



    /**
     * 调用 AI 生成推荐理由
     */
    private String generateAiReason(Long userId, String prompt) {
        try {
            AiChatDTO aiResponse = aiChatService.askQuestion(userId != null ? userId : -1L, prompt);
            return aiResponse != null ? aiResponse.getAnswer() : null;
        } catch (Exception e) {
            // 出错时返回 null，外层使用默认理由
            return null;
        }
    }
}
