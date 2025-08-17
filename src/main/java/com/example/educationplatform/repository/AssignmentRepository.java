package com.example.educationplatform.repository;

import com.example.educationplatform.entity.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    Page<Assignment> findByCourseId(Long courseId, Pageable pageable);


    // 学生端按课程集合批量查（分页）
    Page<Assignment> findByCourseIdIn(Iterable<Long> courseIds, Pageable pageable);

    Boolean existsByCourseIdAndTitle(Long courseId, String title);
}
