package com.example.educationplatform.service;

import com.example.educationplatform.dto.StudentProfileDTO;
import com.example.educationplatform.dto.TeacherProfileDTO;

public interface ProfileService {
    StudentProfileDTO getStudentProfile(Long userId);
    StudentProfileDTO updateStudentProfile(StudentProfileDTO dto ,Long userId);

    TeacherProfileDTO getTeacherProfile(Long userId);
    TeacherProfileDTO updateTeacherProfile(TeacherProfileDTO dto,Long userId);
}
