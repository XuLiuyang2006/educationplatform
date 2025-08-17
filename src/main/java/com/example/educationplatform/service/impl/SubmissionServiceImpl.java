package com.example.educationplatform.service.impl;

import com.example.educationplatform.dto.SubmissionCreateDTO;
import com.example.educationplatform.dto.SubmissionGradeDTO;
import com.example.educationplatform.dto.SubmissionListDTO;
import com.example.educationplatform.entity.Assignment;
import com.example.educationplatform.entity.AssignmentSubmission;
import com.example.educationplatform.entity.Course;
import com.example.educationplatform.enums.CourseStatus;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.exception.BizException;
import com.example.educationplatform.repository.AssignmentRepository;
import com.example.educationplatform.repository.AssignmentSubmissionRepository;
import com.example.educationplatform.repository.CourseRepository;
import com.example.educationplatform.repository.StudentCourseRepository;
import com.example.educationplatform.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentSubmissionRepository submissionRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;

    private Assignment assertAssignmentVisibleToStudent(Long assignmentId, Long studentId) {
        Assignment a = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BizException(ResultCode.PARAM_ERROR, "作业不存在"));
        Course c = courseRepository.findById(a.getCourseId())
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "课程不存在"));
        if (c.getStatus() != CourseStatus.APPROVED) {
            throw new BizException(ResultCode.COURSE_NOT_AVAILABLE, "课程不可用");
        }
        if (!studentCourseRepository.findByStudentIdAndCourseId(studentId, c.getId()).isPresent()) {
            throw new BizException(ResultCode.NO_PERMISSION, "未选该课程，不能提交作业");
        }
        return a;
    }

    private void assertTeacherOwnsAssignment(Long teacherId, Assignment a) {
        Course c = courseRepository.findById(a.getCourseId())
                .orElseThrow(() -> new BizException(ResultCode.COURSE_NOT_FOUND, "课程不存在"));
        if (!teacherId.equals(c.getTeacherId())) {
            throw new BizException(ResultCode.NO_PERMISSION, "无权操作该作业提交");
        }
    }

    @Override
    public void submit(Long studentId, SubmissionCreateDTO dto) {
        Assignment a = assertAssignmentVisibleToStudent(dto.getAssignmentId(), studentId);

        // 截止时间校验（可选：允许迟交可在此扩展）
        if (a.getDueTime() != null && LocalDateTime.now().isAfter(a.getDueTime())) {
            throw new BizException(ResultCode.PARAM_ERROR, "已过截止时间，无法提交");
        }

        // 若已提交，提示或转为更新
        if (submissionRepository.findByAssignmentIdAndStudentId(a.getId(), studentId).isPresent()) {
            throw new BizException(ResultCode.PARAM_ERROR, "已提交，请勿重复提交");
        }

        AssignmentSubmission s = AssignmentSubmission.builder()
                .assignmentId(a.getId())
                .studentId(studentId)
                .content(dto.getContent())
                .attachmentUrl(dto.getAttachmentUrl())
                .submitTime(LocalDateTime.now())
                .build();
        submissionRepository.save(s);
    }

    @Override
    public void updateSubmission(Long studentId, SubmissionCreateDTO dto) {
        Assignment a = assertAssignmentVisibleToStudent(dto.getAssignmentId(), studentId);
        if (a.getDueTime() != null && LocalDateTime.now().isAfter(a.getDueTime())) {
            throw new BizException(ResultCode.PARAM_ERROR, "已过截止时间，不能修改提交");
        }
        AssignmentSubmission s = submissionRepository.findByAssignmentIdAndStudentId(a.getId(), studentId)
                .orElseThrow(() -> new BizException(ResultCode.PARAM_ERROR, "尚未提交，无法更新"));
        s.setContent(dto.getContent());
        s.setAttachmentUrl(dto.getAttachmentUrl());
        s.setSubmitTime(LocalDateTime.now());
        submissionRepository.save(s);
    }

    @Override
    public Page<SubmissionListDTO> listByAssignmentForTeacher(Long teacherId, Long assignmentId, Pageable pageable) {
        Assignment a = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BizException(ResultCode.PARAM_ERROR, "作业不存在"));
        assertTeacherOwnsAssignment(teacherId, a);
        return submissionRepository.findByAssignmentId(assignmentId, pageable)
                .map(s -> new SubmissionListDTO(
                        s.getId(), s.getAssignmentId(), s.getStudentId(),
                        s.getSubmitTime(), s.getScore(), s.getFeedback()
                ));
    }

    @Override
    public void grade(Long teacherId, SubmissionGradeDTO dto) {
        AssignmentSubmission s = submissionRepository.findById(dto.getSubmissionId())
                .orElseThrow(() -> new BizException(ResultCode.PARAM_ERROR, "提交记录不存在"));
        Assignment a = assignmentRepository.findById(s.getAssignmentId())
                .orElseThrow(() -> new BizException(ResultCode.PARAM_ERROR, "作业不存在"));
        assertTeacherOwnsAssignment(teacherId, a);
        s.setScore(dto.getScore());
        s.setFeedback(dto.getFeedback());
        s.setGradeTime(LocalDateTime.now());
        submissionRepository.save(s);
    }

    @Override
    public SubmissionListDTO mySubmission(Long studentId, Long assignmentId) {
        Assignment a = assertAssignmentVisibleToStudent(assignmentId, studentId);
        return submissionRepository.findByAssignmentIdAndStudentId(a.getId(), studentId)
                .map(s -> new SubmissionListDTO(
                        s.getId(), s.getAssignmentId(), s.getStudentId(),
                        s.getSubmitTime(), s.getScore(), s.getFeedback()
                ))
                .orElse(null);
    }
}
