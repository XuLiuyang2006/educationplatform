package com.example.educationplatform.service.impl;

import com.example.educationplatform.dto.CourseCreateDTO;
import com.example.educationplatform.dto.CourseDTO;
import com.example.educationplatform.entity.Course;
import com.example.educationplatform.enums.CourseStatus;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.exception.BizException;
import com.example.educationplatform.repository.CourseRepository;
import com.example.educationplatform.repository.UserRepository;
import com.example.educationplatform.service.TeacherCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherCourseServiceImpl implements TeacherCourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public void createCourse(Long teacherId, CourseCreateDTO dto) {
        Course course = Course.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .contentUrl(dto.getContentUrl())
                .teacherId(teacherId)
                //TODO: 获取教师姓名这里想换一种，我把教师、学生、管理员等用户信息都放在一个表（User）里，后面再改
                .teacherName(userRepository.findById(teacherId)
                        .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND))
                        .getUsername())
                .status(CourseStatus.PENDING) // 默认状态为待审核
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

        if( dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            course.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            course.setDescription(dto.getDescription());
        }
        if (dto.getContentUrl() != null && !dto.getContentUrl().isEmpty()){
            course.setContentUrl(dto.getContentUrl());
        }

        course.setStatus(CourseStatus.PENDING); // 更新时默认状态为待审核

        course.setTeacherName(userRepository.findById(teacherId)
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND))
                .getUsername());
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
