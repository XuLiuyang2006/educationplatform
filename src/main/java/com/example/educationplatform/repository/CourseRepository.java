package com.example.educationplatform.repository;


import com.example.educationplatform.entity.Course;
import com.example.educationplatform.enums.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    //TODO：这个可以提供给公共控制器使用
    List<Course> findByTeacherId(Long teacherId);

    //查找所有课程
    List<Course> findAll();// 这个方法是 JpaRepository 的默认方法，添加参数就不会黄了

    List<Course> findByStatus(CourseStatus status);

    long countByCreateTimeBetween(LocalDateTime start, LocalDateTime end); // 统计在指定时间范围内创建的课程数量
}
