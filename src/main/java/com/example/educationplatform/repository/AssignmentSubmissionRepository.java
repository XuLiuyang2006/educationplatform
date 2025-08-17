package com.example.educationplatform.repository;

import com.example.educationplatform.entity.AssignmentSubmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {

    Optional<AssignmentSubmission> findByAssignmentIdAndStudentId(Long assignmentId, Long studentId);

    Page<AssignmentSubmission> findByAssignmentId(Long assignmentId, Pageable pageable);

    Long countByAssignmentId(Long assignmentId);

    Long countByAssignmentIdAndScoreIsNotNull(Long assignmentId);
}
