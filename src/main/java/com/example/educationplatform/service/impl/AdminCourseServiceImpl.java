package com.example.educationplatform.service.impl;

import com.example.educationplatform.dto.AdminCourseAuditDTO;
import com.example.educationplatform.dto.AdminCourseDTO;
import com.example.educationplatform.dto.AdminCourseDetailDTO;
import com.example.educationplatform.entity.Course;
import com.example.educationplatform.enums.CourseStatus;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.exception.BizException;
import com.example.educationplatform.repository.CourseRepository;
import com.example.educationplatform.service.AdminCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCourseServiceImpl implements AdminCourseService {

    private final CourseRepository courseRepository;

    @Override
    public List<AdminCourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(c -> new AdminCourseDTO(
                        c.getId(),
                        c.getTitle(),
                        c.getDescription(),
                        c.getTeacherName(),
                        c.getTeacherId(),
                        c.getStatus(),
                        c.getCreateTime(),
                        c.getUpdateTime(),
                        c.getAuditBy(),
                        c.getAuditTime(),
                        c.getRejectReason(),
                        c.getVisitCount(),
                        c.getTags()
                ))
                .toList();
    }

    @Override
    public AdminCourseDetailDTO getCourseDetail(Long courseId) {
        // 1. 查找课程
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "课程不存在"));

        // 2. 转换成 DTO
        AdminCourseDetailDTO dto = new AdminCourseDetailDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setContentUrl(course.getContentUrl());
        dto.setTeacherId(course.getTeacherId());
        dto.setTeacherName(course.getTeacherName());
        dto.setTags(course.getTags());
        dto.setStatus(course.getStatus());
        dto.setCreateTime(course.getCreateTime());
        dto.setUpdateTime(course.getUpdateTime());
        dto.setRejectReason(course.getRejectReason());
        dto.setAuditBy(course.getAuditBy());
        dto.setAuditTime(course.getAuditTime());

        return dto;
    }


    @Override
    public List<AdminCourseDTO> getPendingCourses() {
        List<Course> courses = courseRepository.findByStatus(CourseStatus.PENDING);
        return courses.stream()
                .map(c -> new AdminCourseDTO(
                        c.getId(),
                        c.getTitle(),
                        c.getDescription(),
                        c.getTeacherName(),
                        c.getTeacherId(),
                        c.getStatus(),
                        c.getCreateTime(),
                        c.getUpdateTime(),
                        c.getAuditBy(),
                        c.getAuditTime(),
                        c.getRejectReason(),
                        c.getVisitCount(),
                        c.getTags()
                ))
                .toList();
    }


    @Override
    public void auditCourse(AdminCourseAuditDTO dto, Long adminId) {
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "课程不存在"));

        if (course.getStatus() != CourseStatus.PENDING) {
            throw new BizException(ResultCode.INVALID_OPERATION,
                    "课程不是待审核状态，当前状态：" + course.getStatus());
        }

        if (dto.getStatus() == CourseStatus.REJECTED) {
            course.setRejectReason(dto.getReason());
        }

        course.setStatus(dto.getStatus());
        course.setAuditBy(adminId);
        course.setAuditTime(LocalDateTime.now());
        courseRepository.save(course);
    }

    @Override
    public void offlineCourse(Long courseId, Long adminId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "课程不存在"));

        if (course.getStatus() != CourseStatus.APPROVED) {
            throw new BizException(ResultCode.INVALID_OPERATION,
                    "当前课程不支持下架操作，当前状态：" + course.getStatus());
        }

        course.setStatus(CourseStatus.OFFLINE);
        course.setAuditBy(adminId);
        course.setAuditTime(LocalDateTime.now());
        courseRepository.save(course);
    }
}
