package com.example.educationplatform.service.impl;

import com.example.educationplatform.dto.StudentProfileDTO;
import com.example.educationplatform.dto.TeacherProfileDTO;
import com.example.educationplatform.entity.StudentProfile;
import com.example.educationplatform.entity.TeacherProfile;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.exception.BizException;
import com.example.educationplatform.repository.StudentProfileRepository;
import com.example.educationplatform.repository.TeacherProfileRepository;
import com.example.educationplatform.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final StudentProfileRepository studentRepo;
    private final TeacherProfileRepository teacherRepo;

    @Override
    public StudentProfileDTO getStudentProfile(Long userId) {
        StudentProfile profile = studentRepo.findById(userId)
                .orElseThrow(() -> new BizException(ResultCode.USER_PROFILE_NOT_FOUND,"学生档案不存在"));
        StudentProfileDTO dto = new StudentProfileDTO();
        BeanUtils.copyProperties(profile, dto);
        return dto;
    }

    @Override
    public StudentProfileDTO updateStudentProfile(StudentProfileDTO dto,Long userId) {
        //判断当前用户是否是本人
        if(!userId.equals(dto.getUserId())){
            throw new BizException(ResultCode.FORBIDDEN,"无权限修改他人档案");
        }
        StudentProfile profile = new StudentProfile();
        BeanUtils.copyProperties(dto, profile);
        studentRepo.save(profile);
        return dto;
    }

    @Override
    public TeacherProfileDTO getTeacherProfile(Long userId) {
        TeacherProfile profile = teacherRepo.findById(userId)
                .orElseThrow(() -> new BizException(ResultCode.USER_PROFILE_NOT_FOUND,"教师档案不存在"));
        TeacherProfileDTO dto = new TeacherProfileDTO();
        BeanUtils.copyProperties(profile, dto);
        return dto;
    }

    @Override
    public TeacherProfileDTO updateTeacherProfile(TeacherProfileDTO dto,Long userId) {
        //判断当前用户是否是本人
        if(!userId.equals(dto.getUserId())){
            throw new BizException(ResultCode.FORBIDDEN,"无权限修改他人档案");
        }
        TeacherProfile profile = new TeacherProfile();
        BeanUtils.copyProperties(dto, profile);
        teacherRepo.save(profile);
        return dto;
    }
}
