package com.example.educationplatform.service.impl;

import com.example.educationplatform.dto.AssignmentCreateDTO;
import com.example.educationplatform.dto.AssignmentDetailDTO;
import com.example.educationplatform.dto.AssignmentListDTO;
import com.example.educationplatform.entity.Assignment;
import com.example.educationplatform.entity.Course;
import com.example.educationplatform.enums.CourseStatus;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.exception.BizException;
import com.example.educationplatform.repository.AssignmentRepository;
import com.example.educationplatform.repository.AssignmentSubmissionRepository;
import com.example.educationplatform.repository.CourseRepository;
import com.example.educationplatform.repository.StudentCourseRepository;
import com.example.educationplatform.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentSubmissionRepository submissionRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;

    private Course assertCourseOwnedByTeacher(Long courseId, Long teacherId) {
        Course c = courseRepository.findById(courseId)
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "课程不存在"));
        if (!teacherId.equals(c.getTeacherId())) {
            throw new BizException(ResultCode.NO_PERMISSION, "无权操作该课程的作业");
        }
        if (c.getStatus() != CourseStatus.APPROVED) {
            throw new BizException(ResultCode.COURSE_NOT_AVAILABLE, "课程未审核通过或已下架");
        }
        return c;
    }

    @Override
    public void create(Long teacherId, AssignmentCreateDTO dto) {

        //根据作业名称检查有没有创建过这个课程的作业
        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new BizException(ResultCode.PARAM_ERROR, "作业标题不能为空");
        }
        if (assignmentRepository.existsByCourseIdAndTitle(dto.getCourseId(), dto.getTitle())) {
            throw new BizException(ResultCode.PARAM_ERROR, "该课程已存在同名作业");
        }
        // 检查课程是否存在且属于该教师
        assertCourseOwnedByTeacher(dto.getCourseId(), teacherId);

        Assignment a = Assignment.builder()
                .courseId(dto.getCourseId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .dueTime(dto.getDueTime())
                .maxScore(dto.getMaxScore())
                .build();
        assignmentRepository.save(a);
    }

    @Override
    public void update(Long teacherId, Long assignmentId, AssignmentCreateDTO dto) {
        Assignment a = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BizException(ResultCode.PARAM_ERROR, "作业不存在"));
        assertCourseOwnedByTeacher(a.getCourseId(), teacherId);
        a.setTitle(dto.getTitle());
        a.setDescription(dto.getDescription());
        a.setDueTime(dto.getDueTime());
        a.setMaxScore(dto.getMaxScore());
        assignmentRepository.save(a);
    }

    @Override
    public void delete(Long teacherId, Long assignmentId) {
        Assignment a = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BizException(ResultCode.PARAM_ERROR, "作业不存在"));
        assertCourseOwnedByTeacher(a.getCourseId(), teacherId);
        assignmentRepository.deleteById(assignmentId);
    }

    @Override
    public Page<AssignmentListDTO> listByCourse(Long teacherId, Long courseId, Pageable pageable) {
        assertCourseOwnedByTeacher(courseId, teacherId);
        return assignmentRepository.findByCourseId(courseId, pageable)
                .map(a -> new AssignmentListDTO(a.getId(), a.getCourseId(), a.getTitle(), a.getDueTime(), a.getMaxScore(), null));
    }

    @Override
    public AssignmentDetailDTO getDetailForTeacher(Long teacherId, Long assignmentId) {
        Assignment a = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BizException(ResultCode.PARAM_ERROR, "作业不存在"));
        assertCourseOwnedByTeacher(a.getCourseId(), teacherId);
        Long total = submissionRepository.countByAssignmentId(assignmentId);
        Long graded = submissionRepository.countByAssignmentIdAndScoreIsNotNull(assignmentId);
        return new AssignmentDetailDTO(
                a.getId(), a.getCourseId(), a.getTitle(), a.getDescription(),
                a.getDueTime(), a.getMaxScore(), total, graded
        );
    }

    @Override
    public Page<AssignmentListDTO> listForStudent(Long studentId, Pageable pageable) {
        // 找到学生已选课程ID集合
        Set<Long> courseIds = studentCourseRepository.findByStudentId(studentId).stream()
                //使用lambda表达式获取课程ID
                .map(sc->sc.getCourse().getId())
                .collect(Collectors.toSet());
        if (courseIds.isEmpty()) {
            return Page.empty(pageable);
        }
        return assignmentRepository.findByCourseIdIn(courseIds, pageable)
                .map(a -> {
                    boolean submitted = submissionRepository
                            .findByAssignmentIdAndStudentId(a.getId(), studentId).isPresent();
                    return new AssignmentListDTO(a.getId(), a.getCourseId(), a.getTitle(), a.getDueTime(), a.getMaxScore(), submitted);
                });
    }

    @Override
    public AssignmentDetailDTO getDetailForStudent(Long studentId, Long assignmentId) {
        Assignment a = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BizException(ResultCode.PARAM_ERROR, "作业不存在"));
        // 校验该学生是否选了该课程
        boolean enrolled = studentCourseRepository.findByStudentIdAndCourseId(studentId, a.getCourseId()).isPresent();
        if (!enrolled) throw new BizException(ResultCode.NO_PERMISSION, "未选该课程，无法查看作业详情");
        Long total = submissionRepository.countByAssignmentId(assignmentId);
        Long graded = submissionRepository.countByAssignmentIdAndScoreIsNotNull(assignmentId);
        return new AssignmentDetailDTO(
                a.getId(), a.getCourseId(), a.getTitle(), a.getDescription(),
                a.getDueTime(), a.getMaxScore(), total, graded
        );
    }
}
