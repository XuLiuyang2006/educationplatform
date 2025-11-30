package com.example.educationplatform.service.impl;

import com.example.educationplatform.dto.StudentCourseCreateDTO;
import com.example.educationplatform.dto.StudentCourseDetailDTO;
import com.example.educationplatform.dto.StudentCourseListDTO;
import com.example.educationplatform.entity.Course;
import com.example.educationplatform.entity.StudentCourse;
import com.example.educationplatform.enums.CourseStatus;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.exception.BizException;
import com.example.educationplatform.repository.CourseRepository;
import com.example.educationplatform.repository.StudentCourseRepository;
import com.example.educationplatform.service.StudentCourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//@Tag(name = "学生选课服务", description = "处理学生选课相关的业务逻辑")
@Service
@RequiredArgsConstructor
public class StudentCourseServiceImpl implements StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final CourseRepository courseRepository;

    /**
     * 将 StudentCourse 实体转换为 StudentCourseListDTO
     * @param studentCourse 选课记录实体
     * @return 转换后的 DTO
     */
    private StudentCourseListDTO studentCourseListToDTO(StudentCourse studentCourse) {
        StudentCourseListDTO dto = new StudentCourseListDTO();
        // 手动复制属性
        dto.setId(studentCourse.getCourse().getId());
        dto.setTitle(studentCourse.getCourse().getTitle());
        dto.setContentUrl(studentCourse.getCourse().getContentUrl());
        return dto;
    }
    /**
     * 将 Course 实体转换为 StudentCourseListDTO
     * @param course 课程实体
     * @return 转换后的 DTO
     */
    private StudentCourseListDTO studentCourseListToDTO(Course course) {
        StudentCourseListDTO dto = new StudentCourseListDTO();
        // 自动复制属性，这里的 Course 与 StudentCourseListDTO 有相同的属性
        BeanUtils.copyProperties(course,dto);
        return dto;
    }

    @Override
    @NonNull
    public List<StudentCourseListDTO> getAllCourses() {
        // 获取所有课程
        return courseRepository.findByStatus(CourseStatus.APPROVED)
                .stream()
                .map(this::studentCourseListToDTO)
                .collect(Collectors.toList());
    }

    //提供接口给接下来可能存在的公共控制器使用
    @Override
    public List<StudentCourseListDTO> getCoursesByStatus(CourseStatus status) {
        // 获取指定状态的课程
        return courseRepository.findByStatus(status)
                .stream()
                .map(this::studentCourseListToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void enrollCourse(Long studentId, Long courseId) {

        // 检查课程是否存在
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "课程不存在"));

        // 检查是否已选课
        if (studentCourseRepository.findByStudentIdAndCourseId(studentId, courseId).isPresent()) {
            throw new BizException(ResultCode.ALREADY_ENROLLED, "您已选过此课程");
        }
        // 创建选课记录
//        StudentCourse studentCourse = new StudentCourse();
//        studentCourse.setStudentId(studentId);
//        studentCourse.setCourseId(courseId);
//        studentCourseRepository.save(studentCourse);

        //检查该课程状态是否允许选课
        if (course.getStatus() != CourseStatus.APPROVED) {
            throw new BizException(ResultCode.COURSE_NOT_AVAILABLE, "课程状态不允许选课，当前课程状态：" + course.getStatus());
        }

        // 使用构建者模式(@Builder)创建选课记录
        StudentCourse sc = StudentCourse.builder()
                .studentId(studentId)
                .course(course)
                .progress(0.0)
                .title(course.getTitle())
                .build();
        studentCourseRepository.save(sc);
    }

    @Override
    @Transactional
    public void withdrawCourse(Long studentId, Long courseId) {
        //TODO：记得添加检查是不是本人操作的逻辑


        // 检查是否已选课
        StudentCourse studentCourse = studentCourseRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "未找到选课记录"));
        // 删除选课记录
        studentCourseRepository.delete(studentCourse);
    }

    @Override
    public List<StudentCourseListDTO> getMyCourses(Long studentId) {
        // 获取学生的所有选课记录
        return studentCourseRepository.findByStudentId(studentId)
                .stream()
                .map(this::studentCourseListToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateProgress(Long studentId, StudentCourseCreateDTO dto){

        //检查课程是否存在
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "课程不存在"));

        // 如果课程状态不允许更新进度
        if(course.getStatus() != CourseStatus.APPROVED) {
            throw new BizException(ResultCode.COURSE_NOT_AVAILABLE, "课程状态不允许更新进度，当前课程状态：" + course.getStatus());
        }

        // 检查选课记录是否存在
        StudentCourse studentCourse = studentCourseRepository.findByStudentIdAndCourseId(studentId, dto.getCourseId())
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "未找到选课记录"));

        // 更新进度
        studentCourse.setProgress(dto.getProgress());
        studentCourseRepository.save(studentCourse);
    }

    @Override
    public Double getProgress(Long studentId, Long courseId){

        // 检查课程是否存在
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "课程不存在"));

        // 如果课程状态不允许查询进度
        if (course.getStatus() != CourseStatus.APPROVED) {
            throw new BizException(ResultCode.COURSE_NOT_AVAILABLE, "课程状态不允许查询进度，当前课程状态：" + course.getStatus());
        }

        // 检查选课记录是否存在
        StudentCourse studentCourse = studentCourseRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "未找到选课记录"));

        return studentCourse.getProgress();

    }

    @Override
    public Page<StudentCourseListDTO> selectByStudentId(Long studentId, Pageable pageable) {
        Page<StudentCourse> page = studentCourseRepository.findByStudentId(studentId, pageable);
        return page.map(sc -> new StudentCourseListDTO(//需要在 StudentCourseListDTO 中添加对应的构造函数，@AllArgsConstructor 或 @Builder 注解
                sc.getId(),
                sc.getCourse().getTitle(),
                sc.getCourse().getContentUrl()
        ));

    }

    @Override
    public StudentCourseDetailDTO getCourseDetail(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "课程不存在"));
        StudentCourseDetailDTO dto = new StudentCourseDetailDTO();
        BeanUtils.copyProperties(course, dto);
        return dto;
    }

}
