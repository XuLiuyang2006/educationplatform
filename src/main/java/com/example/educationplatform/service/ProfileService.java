package com.example.educationplatform.service;

import com.example.educationplatform.dto.StudentProfileDTO;
import com.example.educationplatform.dto.TeacherProfileDTO;

public interface ProfileService {
    //Student
    boolean existsStudentProfile(Long userId);
    StudentProfileDTO getStudentProfile(Long userId);
    StudentProfileDTO createStudentProfile(StudentProfileDTO dto);
    StudentProfileDTO updateStudentProfile(StudentProfileDTO dto);

    //Teacher
    boolean existsTeacherProfile(Long userId);
    TeacherProfileDTO getTeacherProfile(Long userId);
    TeacherProfileDTO createTeacherProfile(TeacherProfileDTO dto);
    TeacherProfileDTO updateTeacherProfile(TeacherProfileDTO dto);
}
