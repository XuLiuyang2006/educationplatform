package com.example.educationplatform.service;

import com.example.educationplatform.dto.SubmissionCreateDTO;
import com.example.educationplatform.dto.SubmissionGradeDTO;
import com.example.educationplatform.dto.SubmissionListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubmissionService {

    void submit(Long studentId, SubmissionCreateDTO dto);

    void updateSubmission(Long studentId, SubmissionCreateDTO dto); // 截止前允许修改（可选）

    Page<SubmissionListDTO> listByAssignmentForTeacher(Long teacherId, Long assignmentId, Pageable pageable);

    void grade(Long teacherId, SubmissionGradeDTO dto);

    SubmissionListDTO mySubmission(Long studentId, Long assignmentId);
}
