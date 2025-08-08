package com.example.educationplatform.service;

import com.example.educationplatform.dto.StudentCourseDTO;
import com.example.educationplatform.entity.Course;
import com.example.educationplatform.entity.StudentCourse;

import java.util.List;

public interface StudentCourseService {

    // 学生查询所有课程
    List<Course> getAllCourses();

    // 学生选课
    void enrollCourse(Long studentId, Long courseId);

    // 学生退课
    void withdrawCourse(Long studentId, Long courseId);

    // 获取学生的所有选课记录
     List<StudentCourse> getMyCourses(Long studentId);

    // 更新学生的课程进度
    void updateProgress(Long studentId, StudentCourseDTO dto);

    // 获取学生在某门课程的进度
    Double getProgress(Long studentId, Long courseId);

}
