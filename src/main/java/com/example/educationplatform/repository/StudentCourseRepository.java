package com.example.educationplatform.repository;

import com.example.educationplatform.entity.StudentCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {

    // 根据学生ID和课程ID查询选课记录
    Optional<StudentCourse> findByStudentIdAndCourseId(Long studentId, Long courseId);

    // 根据学生ID查询所有选课记录
    List<StudentCourse> findByStudentId(Long studentId);

    // 查看某段时间内的选课记录数量
    long countBySelectedAtBetween(LocalDateTime start, LocalDateTime end);

    // 删除选课记录
    void deleteByStudentIdAndCourseId(Long studentId, Long courseId);

    // 分页查询（带 course 一并加载）
    @EntityGraph(attributePaths = "course")
    Page<StudentCourse> findByStudentId(Long studentId, Pageable pageable);

    // 根据课程id统计选课人数
    Long countByCourseId(Long courseId);

//    // 🔥 热门排行榜：统计课程选课人数并按人数降序排序
//    @Query("SELECT sc.course FROM StudentCourse sc GROUP BY sc.course ORDER BY COUNT(sc.course) DESC")
//    Page<Course> findPopularCourses(Pageable pageable);
}
