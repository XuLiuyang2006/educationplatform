package com.example.educationplatform.service;

import java.time.LocalDate;

public interface AdminStatsService {

    // 获取课程总数
    long getCourseCount();

    // 获取学生人数
    long getStudentCount();

    // 获取教师人数
    long getTeacherCount();

    // 获取某时间段新增注册人数
    long getNewUserCount(LocalDate start, LocalDate end);

    // 获取某时间段新增课程数
    long getNewCourseCount(LocalDate start, LocalDate end);
}
