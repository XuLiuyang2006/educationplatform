package com.example.educationplatform.service;

import com.example.educationplatform.dto.AdminCourseAuditDTO;
import com.example.educationplatform.entity.Course;
import org.aspectj.apache.bcel.generic.LineNumberGen;

import java.util.List;

public interface AdminCourseService {

    // 查询待审核课表
    List<Course> getPendingCourses();

    // 查询所有课表
    List<Course> getAllCourses();

    // 审核课程
    void auditCourse(AdminCourseAuditDTO dto, Long adminId);

    // 下架课程
    void offlineCourse(Long courseId, Long adminId);
}
