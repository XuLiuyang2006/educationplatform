package com.example.educationplatform.service.impl;

import com.example.educationplatform.dto.LoginDTO;
import com.example.educationplatform.dto.UserDTO;
import com.example.educationplatform.entity.User;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.enums.UserStatus;
import com.example.educationplatform.exception.BizException;
import com.example.educationplatform.repository.UserRepository;
import com.example.educationplatform.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final HttpSession session;

    @Override
    @Transactional
    public void register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BizException(ResultCode.USERNAME_EXISTS);
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new BizException(ResultCode.PASSWORD_NOT_NULL, "密码不能为空");
        }

        // 检查邮箱是否为空
        if (user.getEmail() == null||user.getEmail().isEmpty() ) {
            throw new BizException(ResultCode.EMAIL_NOT_NULl);
        }

        // 检查邮箱是否已注册
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BizException(ResultCode.EMAIL_EXISTS);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BizException(ResultCode.LOGIN_FAILED);
        }

        //判断用户状态是否可以登录
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new BizException(ResultCode.FORBIDDEN, "用户状态不允许登录，当前状态：" + user.getStatus());
        }

        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        session.setAttribute("user", dto); // 将用户信息存入Session
        session.setAttribute("userId", user.getId()); // 存储用户ID
        session.setAttribute("role", user.getRole()); // 存储用户角色
        return dto;
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND));

        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    @Override
    @Transactional
    public void update(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND));

        // 如果密码不为空，则加密后更新
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // 更新其他字段
        //在更新用户名的时候需要检查是否存在同名用户并判断同名用户是不是自己
        if(user.getUsername()!= null && !user.getUsername().isEmpty()) {
            if (userRepository.existsByUsername(user.getUsername())&& !user.getUsername().equals(existingUser.getUsername())) {
                throw new BizException(ResultCode.USERNAME_EXISTS);
            }else {
                existingUser.setUsername(user.getUsername());
            }
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new BizException(ResultCode.EMAIL_EXISTS);
            }else {
                existingUser.setEmail(user.getEmail());
            }
        }
        if (user.getPhone() != null && !user.getPhone().isEmpty()) {
            existingUser.setPhone(user.getPhone());
        }

        userRepository.save(existingUser);
    }
}