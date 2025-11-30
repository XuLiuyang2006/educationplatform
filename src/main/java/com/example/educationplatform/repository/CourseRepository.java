package com.example.educationplatform.repository;

import com.example.educationplatform.dto.StudentCourseListDTO;
import com.example.educationplatform.entity.Course;
import com.example.educationplatform.enums.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    //TODO：这个可以提供给公共控制器使用
    List<Course> findByTeacherId(Long teacherId);

    List<Course> findByStatus(CourseStatus status);

    Page<Course> findByStatus(CourseStatus status, Pageable pageable);

    long countByCreateTimeBetween(LocalDateTime start, LocalDateTime end); // 统计在指定时间范围内创建的课程数量

    List<StudentCourseListDTO> getCoursesByStatus(CourseStatus status);

    // 首页推荐课程，简单实现为最新和热门
    List<Course> findTop10ByOrderByCreateTimeDesc(); // 最新课程
    List<Course> findTop5ByOrderByIdDesc();         // 简单模拟热门（可扩展点击量/访问日志）
    List<Course> findByTitleContainingIgnoreCase(String keyword); // 按关键词查

    @Query("SELECT c FROM Course c WHERE c.id <> :courseId AND c.tags LIKE %:tag%")
    List<Course> findByTag(@Param("courseId") Long courseId, @Param("tag") String tag);

    List<Course> findByTagsContainingIgnoreCase(String tag);

}
