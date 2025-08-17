package com.example.educationplatform.service;

import com.example.educationplatform.dto.AssignmentCreateDTO;
import com.example.educationplatform.dto.AssignmentDetailDTO;
import com.example.educationplatform.dto.AssignmentListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssignmentService {

    void create(Long teacherId, AssignmentCreateDTO dto);

    void update(Long teacherId, Long assignmentId, AssignmentCreateDTO dto);

    void delete(Long teacherId, Long assignmentId);

    Page<AssignmentListDTO> listByCourse(Long teacherId, Long courseId, Pageable pageable);

    AssignmentDetailDTO getDetailForTeacher(Long teacherId, Long assignmentId);

    Page<AssignmentListDTO> listForStudent(Long studentId, Pageable pageable);

    AssignmentDetailDTO getDetailForStudent(Long studentId, Long assignmentId);
}
