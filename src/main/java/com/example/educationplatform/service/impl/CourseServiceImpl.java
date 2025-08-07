package com.example.educationplatform.service.impl;

import com.example.educationplatform.dto.CourseCreateDTO;
import com.example.educationplatform.dto.CourseDTO;
import com.example.educationplatform.entity.Course;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.exception.BizException;
import com.example.educationplatform.repository.CourseRepository;
import com.example.educationplatform.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public void createCourse(Long teacherId, CourseCreateDTO dto) {
        Course course = Course.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .contentUrl(dto.getContentUrl())
                .teacherId(teacherId)
                .build();
        courseRepository.save(course);
    }

    @Override
    public void updateCourse(Long courseId, Long teacherId, CourseCreateDTO dto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND));

        if (!course.getTeacherId().equals(teacherId)) {
            throw new BizException(ResultCode.NO_PERMISSION);
        }

        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setContentUrl(dto.getContentUrl());
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long courseId, Long teacherId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BizException(ResultCode.COURSE_CREATION_FAILED));

        if (!course.getTeacherId().equals(teacherId)) {
            throw new BizException(ResultCode.NO_PERMISSION);
        }

        courseRepository.delete(course);
    }

    @Override
    public List<CourseDTO> listMyCourses(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId).stream().map(course -> {
            CourseDTO dto = new CourseDTO();
            BeanUtils.copyProperties(course, dto);
            return dto;
        }).collect(Collectors.toList());
    }
}
