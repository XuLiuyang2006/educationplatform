package com.example.educationplatform.repository;

import com.example.educationplatform.entity.Course;
import com.example.educationplatform.entity.StudentCourse;
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
    long countByCreateTimeBetween(LocalDateTime start, LocalDateTime end);

    // 删除选课记录
    void deleteByStudentIdAndCourseId(Long studentId, Long courseId);
}
