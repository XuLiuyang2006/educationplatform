package com.example.educationplatform.aop;

import com.example.educationplatform.annotation.RoleRequired;
import com.example.educationplatform.dto.UserDTO;
import com.example.educationplatform.enums.RoleEnum;
import com.example.educationplatform.exception.BizException;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.entity.User;
import com.example.educationplatform.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleCheckAspect {

    private final HttpSession session;
    private final UserRepository userRepository;

    @Before("@annotation(roleRequired)")
    public void checkRole(JoinPoint joinPoint, RoleRequired roleRequired) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND)); // 用户不存在

        if (user == null) {
            throw new BizException(ResultCode.NOT_LOGIN);
        }

        RoleEnum userRole = user.getRole();

        //TODO:这里不太明白，就是这个:的作用
        for (RoleEnum allowed : roleRequired.value()) {
            if (allowed == userRole) {
                return;
            }
        }

        throw new BizException(ResultCode.FORBIDDEN); // 角色无权限访问
    }
}
