package com.example.educationplatform.service;

import com.example.educationplatform.dto.CourseCreateDTO;
import com.example.educationplatform.dto.CourseDTO;

import java.util.List;

public interface CourseService {

    void createCourse(Long teacherId, CourseCreateDTO dto);

    void updateCourse(Long courseId, Long teacherId, CourseCreateDTO dto);

    void deleteCourse(Long courseId, Long teacherId);

    List<CourseDTO> listMyCourses(Long teacherId);
}
