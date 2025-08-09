package com.example.educationplatform.service.impl;

import com.example.educationplatform.dto.AdminCourseAuditDTO;
import com.example.educationplatform.entity.Course;
import com.example.educationplatform.enums.CourseStatus;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.exception.BizException;
import com.example.educationplatform.repository.CourseRepository;
import com.example.educationplatform.service.AdminCourseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCourseServiceImpl implements AdminCourseService {

    private final CourseRepository courseRepository;

    @Override
    public List<Course> getPendingCourses() {
        // 查询待审核的课程
        return courseRepository.findByStatus(CourseStatus.PENDING);
    }

    @Override
    public List<Course> getAllCourses() {
        // 查询所有课程
        return courseRepository.findAll();
    }

    //审核课程
    @Override
    public void auditCourse(AdminCourseAuditDTO dto, Long adminId) {

        // 获取课程
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND,"课程不存在"));

        // 查看课程状态
        if (course.getStatus() != CourseStatus.PENDING) {
            throw new BizException(ResultCode.INVALID_OPERATION, "课程不是待审核状态，当前状态：" + course.getStatus());
        }

        //如果课程被拒绝的话就设置被拒绝理由
        if(dto.getStatus() == CourseStatus.REJECTED) {
            course.setRejectReason(dto.getReason());
        }
        course.setStatus(dto.getStatus());
        course.setAuditBy(adminId);
        course.setAuditTime(java.time.LocalDateTime.now());
        courseRepository.save(course);

    }

    //此处是课程下架，对已经上架的课程执行的强制下架操作，教师和管理员都可以访问的接口
    @Override
    public void offlineCourse(Long courseId, Long adminId) {
        // 获取课程
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND,"课程不存在"));


        //判断当前课程有没有成功上线
        if (course.getStatus() != CourseStatus.APPROVED) {
            throw new BizException(ResultCode.INVALID_OPERATION,"当前课程不支持下架操作，当前状态：" + course.getStatus());
        }

//        // 下架课程
//        course.setStatus(CourseStatus.OFFLINE);
//        course.setAuditBy(adminId);
//        course.setAuditTime(LocalDateTime.now());
//        courseRepository.save(course);

        //使用同一设置课程状态方法设置课程状态
        // 下架课程
        updateCourseStatus(courseId,adminId,CourseStatus.OFFLINE);
    }


    //统一设置课程状态
    private void updateCourseStatus(Long id, Long adminId, CourseStatus status) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "课程不存在"));

        //设置课程状态
        course.setStatus(status);
        course.setAuditBy(adminId);
        course.setAuditTime(LocalDateTime.now());
        courseRepository.save(course);
    }
}
