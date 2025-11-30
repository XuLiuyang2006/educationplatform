package com.example.educationplatform.service;

import com.example.educationplatform.dto.StudentCourseCreateDTO;
import com.example.educationplatform.dto.StudentCourseDetailDTO;
import com.example.educationplatform.dto.StudentCourseListDTO;
import com.example.educationplatform.enums.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentCourseService {

    // TODO：学生查询所有课程，后续移动到公共控制器中完成
    List<StudentCourseListDTO> getAllCourses();

    // 学生选课
    void enrollCourse(Long studentId, Long courseId);

    // 学生退课
    void withdrawCourse(Long studentId, Long courseId);

    //根据状态获取课程
    List<StudentCourseListDTO> getCoursesByStatus(CourseStatus status);

    // 获取该学生的所有选课记录
     List<StudentCourseListDTO> getMyCourses(Long studentId);

    // 更新学生的课程进度
    void updateProgress(Long studentId, StudentCourseCreateDTO dto);

    // 获取学生在某门课程的进度
    Double getProgress(Long studentId, Long courseId);

    //分页查询默认分页0，10
    Page<StudentCourseListDTO> selectByStudentId(Long studentId, Pageable pageable);

    //获取课程的详细信息
    StudentCourseDetailDTO getCourseDetail(Long courseId);

    // TODO:根据课程id，获取某门课程内容，放到公共控制器中实现
}
