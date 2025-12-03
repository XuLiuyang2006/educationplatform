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

@Service //用户个人资料服务实现类, 处理用户个人资料相关的业务逻辑,实现类需要写@Service注解
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final StudentProfileRepository studentProfileRepository;
    private final TeacherProfileRepository teacherProfileRepository;

    //StudentProfileRepository学生个人资料仓库接口
    @Override
    public boolean existsStudentProfile(Long userId) {
        return studentProfileRepository.existsById(userId);
    }

    @Override
    public StudentProfileDTO getStudentProfile(Long userId) {
        StudentProfile profile = studentProfileRepository.findById(userId)
                .orElseThrow(() -> new BizException(ResultCode.USER_PROFILE_NOT_FOUND, "学生个人资料不存在"));
        StudentProfileDTO dto = new StudentProfileDTO();
        BeanUtils.copyProperties(profile, dto);
        return dto;
    }

    @Override
    public StudentProfileDTO createStudentProfile(StudentProfileDTO dto) {
        //先检查是否已存在个人资料,如果存在则抛出异常,否则创建新的个人资料
        if (studentProfileRepository.existsById(dto.getUserId())) {
            throw new BizException(ResultCode.USER_PROFILE_ALREADY_EXISTS, "学生个人资料已存在");
        }
        StudentProfile profile = new StudentProfile();
        BeanUtils.copyProperties(dto, profile);
        studentProfileRepository.save(profile);
        return dto;
    }

    @Override
    public StudentProfileDTO updateStudentProfile(StudentProfileDTO dto) {
        StudentProfile profile = studentProfileRepository.findById(dto.getUserId())
                .orElseThrow(() -> new BizException(ResultCode.USER_PROFILE_NOT_FOUND, "学生个人资料不存在"));
        //更新个人资料属性，非空字段才更新，避免覆盖已有数据
        if(dto.getMajor() != null && !dto.getMajor().isEmpty())
            profile.setMajor(dto.getMajor());
        if(dto.getGrade() != null && !dto.getGrade().isEmpty())
            profile.setGrade(dto.getGrade());
        //接下来是interests\goals。（这样写注释，IDEA可以自动提示）
        if(dto.getInterests() != null && !dto.getInterests().isEmpty())
            profile.setInterests(dto.getInterests());
        if(dto.getGoals() != null && !dto.getGoals().isEmpty())
            profile.setGoals(dto.getGoals());
        //保存更新后的个人资料
        studentProfileRepository.save(profile);
        //返回更新后的DTO
        StudentProfileDTO updatedDto = new StudentProfileDTO();
        BeanUtils.copyProperties(profile, updatedDto);
        return updatedDto;
    }

    //Teacher部分的Profile相关操作
    @Override
    public boolean existsTeacherProfile(Long userId) {
        return teacherProfileRepository.existsById(userId);
    }

    @Override
    public TeacherProfileDTO getTeacherProfile(Long userId) {
        TeacherProfile profile = teacherProfileRepository.findById(userId)
                .orElseThrow(() -> new BizException(ResultCode.USER_PROFILE_NOT_FOUND, "教师个人资料不存在"));
        TeacherProfileDTO dto = new TeacherProfileDTO();
        BeanUtils.copyProperties(profile, dto);
        return dto;
    }

    @Override
    public TeacherProfileDTO createTeacherProfile(TeacherProfileDTO dto) {
        //先检查是否已存在个人资料,如果存在则抛出异常,否则创建新的个人资料
        if (teacherProfileRepository.existsById(dto.getUserId())) {
            throw new BizException(ResultCode.USER_PROFILE_ALREADY_EXISTS, "教师个人资料已存在");
        }
        TeacherProfile profile = new TeacherProfile();
        BeanUtils.copyProperties(dto, profile);
        //保存个人资料,返回DTO
        teacherProfileRepository.save(profile);
        return dto;
    }
    @Override
    public TeacherProfileDTO updateTeacherProfile(TeacherProfileDTO dto) {
        TeacherProfile profile = teacherProfileRepository.findById(dto.getUserId())
                .orElseThrow(() -> new BizException(ResultCode.USER_PROFILE_NOT_FOUND, "教师个人资料不存在"));
        //更新个人资料属性，非空字段才更新，避免覆盖已有数据
        if (dto.getExpertise() != null)
            profile.setExpertise(dto.getExpertise());
        if (dto.getTags() != null)
            profile.setTags(dto.getTags());
        if (dto.getTeachingStyle() != null)
            profile.setTeachingStyle(dto.getTeachingStyle());
        //保存更新后的个人资料
        teacherProfileRepository.save(profile);
        //返回更新后的DTO
        TeacherProfileDTO updatedDto = new TeacherProfileDTO();
        BeanUtils.copyProperties(profile, updatedDto);
        return updatedDto;
    }
}