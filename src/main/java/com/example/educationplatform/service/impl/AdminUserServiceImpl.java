package com.example.educationplatform.service.impl;

import com.example.educationplatform.annotation.RoleRequired;
import com.example.educationplatform.dto.UserAdminDTO;
import com.example.educationplatform.entity.User;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.enums.RoleEnum;
import com.example.educationplatform.enums.UserStatus;
import com.example.educationplatform.exception.BizException;
import com.example.educationplatform.repository.UserRepository;
import com.example.educationplatform.service.AdminUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@RoleRequired(RoleEnum.ADMIN) // 确保只有管理员可以访问此服务
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    private UserAdminDTO toDTO(User user) {
        UserAdminDTO dto = new UserAdminDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        return dto;
    }

    //TODO：不能返回类型是实体类，不然会暴露数据库结构
    @Override
    public List<UserAdminDTO> getAllUsers(RoleEnum role, UserStatus status) {
        List<User> users;
        if (role != null){
            users = userRepository.findByRole(role);
        } else if (status != null) {
            users = userRepository.findByStatus(status);
        } else {
            users = userRepository.findAll();
        }
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserAdminDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND, "用户不存在"));
    }

    @Override
    public void approveUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND, "用户不存在"));
        if (user.getStatus() != UserStatus.PENDING) {
            throw new BizException(ResultCode.INVALID_OPERATION, "用户状态不允许此操作");
        }
        updateStatus(id, UserStatus.ACTIVE);
    }

    @Override
    public void rejectUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND, "用户不存在"));
        if (user.getStatus() != UserStatus.PENDING) {
            throw new BizException(ResultCode.INVALID_OPERATION, "用户状态不允许此操作");
        }
        updateStatus(id, UserStatus.REJECTED);
    }

    @Override
    public void banUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND, "用户不存在"));
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new BizException(ResultCode.INVALID_OPERATION, "用户状态不允许此操作");
        }
        updateStatus(id, UserStatus.BANNED);
    }

    @Override
    public void unbanUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND, "用户不存在"));
        if(user.getStatus() != UserStatus.BANNED){
            throw new BizException(ResultCode.INVALID_OPERATION, "用户状态不允许此操作");
        }

        updateStatus(id, UserStatus.ACTIVE);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BizException(ResultCode.USER_NOT_FOUND, "用户不存在");
        }
        userRepository.deleteById(id);
    }

    // 统一处理状态更新
    private void updateStatus(Long id, UserStatus status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND, "用户不存在"));
        user.setStatus(status);
        userRepository.save(user);
    }


}
