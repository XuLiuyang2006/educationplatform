package com.example.educationplatform.service.impl;

import com.example.educationplatform.dto.PublicCourseDetailDTO;
import com.example.educationplatform.dto.PublicCourseListDTO;
import com.example.educationplatform.entity.Course;
import com.example.educationplatform.enums.CourseStatus;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.exception.BizException;
import com.example.educationplatform.repository.CourseRepository;
import com.example.educationplatform.repository.StudentCourseRepository;
import com.example.educationplatform.service.PublicCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicCourseServiceImpl implements PublicCourseService {

    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;

    @Override
    public Page<PublicCourseListDTO> getAllCoursesList(Pageable pageable) {
        return courseRepository.findByStatus(CourseStatus.APPROVED,pageable)
                .map(course -> new PublicCourseListDTO(
                        course.getId(),
                        course.getTitle(),
                        course.getDescription(),
                        course.getStatus(),
                        course.getTags()
                ));
    }

    @Override
    public PublicCourseDetailDTO getCourseDetail(Long courseId) {
        // 获取课程详情
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND,"课程不存在"));

        course.setVisitCount(course.getVisitCount() + 1);
        courseRepository.save(course);

        // 获取选课人数
        Long studentCount = studentCourseRepository.countByCourseId(courseId);

        // 构造 PublicCourseDetailDTO
        return new PublicCourseDetailDTO(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getTags(),
                course.getCreateTime(),
                course.getStatus(),
                course.getContentUrl(),
                course.getTeacherName(),
                studentCount,
                course.getVisitCount()
        );
    }
}
