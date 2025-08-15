package com.example.educationplatform.repository;

import com.example.educationplatform.entity.CourseMaterials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CourseMaterialsRepository extends JpaRepository<CourseMaterials, Long> {

    // 根据课程ID查询所有课程材料
    List<CourseMaterials> findByCourseId(Long courseId);

    // 根据ID删除课程材料
    void deleteById(Long id);

    // 根据ID查询课程材料
    Optional<CourseMaterials> findById(Long id);
}
