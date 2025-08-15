package com.example.educationplatform.service.impl;

import com.example.educationplatform.dto.TeacherCourseCreateDTO;
import com.example.educationplatform.dto.TeacherCourseListDTO;
import com.example.educationplatform.entity.Course;
import com.example.educationplatform.entity.CourseMaterials;
import com.example.educationplatform.enums.CourseStatus;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.exception.BizException;
import com.example.educationplatform.repository.CourseMaterialsRepository;
import com.example.educationplatform.repository.CourseRepository;
import com.example.educationplatform.repository.UserRepository;
import com.example.educationplatform.service.TeacherCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherCourseServiceImpl implements TeacherCourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseMaterialsRepository courseMaterialsRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;  // 从 application.yml 配置读取，如：/uploaded/course_resources/



    private TeacherCourseListDTO teacherCourseListToDTO(Course course) {
        TeacherCourseListDTO teacherCourseListDTO = new TeacherCourseListDTO();
        BeanUtils.copyProperties(course,teacherCourseListDTO);
        return teacherCourseListDTO;
    }

    @Override
    public void createCourse(Long teacherId, TeacherCourseCreateDTO dto) {
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
    public void updateCourse(Long courseId, Long teacherId, TeacherCourseCreateDTO dto) {
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
    public List<TeacherCourseListDTO> listMyCoursesList(Long teacherId) {
        return courseRepository.findAll()
                .stream()
                .map(this::teacherCourseListToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String uploadCourseResource(Long teacherId, Long courseId, MultipartFile file) {
        // 1. 校验课程是否存在且归属教师
        var courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            throw new BizException(ResultCode.COURSE_NOT_FOUND, "课程不存在或已被删除");
        }
        var course = courseOpt.get();
        if (!course.getTeacherId().equals(teacherId)) {
            throw new BizException(ResultCode.NO_PERMISSION,"无权限上传该课程资源");
        }

        // 2. 校验文件
        if (file.isEmpty()) {
            throw new BizException(ResultCode.INVALID_OPERATION,"上传文件不能为空");
        }

        // 3. 文件保存逻辑
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        // 避免文件名重复，使用UUID命名
        String newFileName = System.currentTimeMillis() + "-" + UUID.randomUUID() + fileExtension;

        try {
            // 创建目录（如果不存在）
            File dir = new File(uploadDir,"course_resources");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 保存文件
            File destFile = new File(dir, newFileName);
            file.transferTo(destFile);

            // 4. 保存数据库记录
            CourseMaterials material = new CourseMaterials();
            material.setCourseId(courseId);
            material.setFileName(newFileName);
            material.setOriginalFileName(originalFilename);
            material.setFilePath("/uploads/course_resources/" + newFileName);  // 供前端访问的路径，根据nginx配置调整
            courseMaterialsRepository.save(material);

            // 5. 返回文件访问URL
            return material.getFilePath();

        } catch (IOException e) {
            throw new BizException(ResultCode.FILE_UPLOAD_FAILED,"文件上传失败:" + e.getMessage());
        }
    }
}
