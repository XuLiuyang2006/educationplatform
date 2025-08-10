package com.example.educationplatform.service;

import com.example.educationplatform.dto.TeacherCourseCreateDTO;
import com.example.educationplatform.dto.TeacherCourseListDTO;

import java.util.List;

public interface TeacherCourseService {

    // 教师创建课程
    void createCourse(Long teacherId, TeacherCourseCreateDTO dto);

    // 教师更新课程
    void updateCourse(Long courseId, Long teacherId, TeacherCourseCreateDTO dto);

    // 教师删除课程
    void deleteCourse(Long courseId, Long teacherId);

    // 教师查询自己的所有课程
    List<TeacherCourseListDTO> listMyCoursesList(Long teacherId);

    //TODO：查询所有课程，查询某一门课程的内容等功能可以放到公共控制器中实现
}
