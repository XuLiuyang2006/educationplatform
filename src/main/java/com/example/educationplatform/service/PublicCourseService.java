package com.example.educationplatform.service;

import com.example.educationplatform.dto.PublicCourseDetailDTO;
import com.example.educationplatform.dto.PublicCourseListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PublicCourseService {
    Page<PublicCourseListDTO> getAllCoursesList(Pageable pageable);

    PublicCourseDetailDTO getCourseDetail(Long courseId); // 获取课程详情，课程内容、选课人数等

}
