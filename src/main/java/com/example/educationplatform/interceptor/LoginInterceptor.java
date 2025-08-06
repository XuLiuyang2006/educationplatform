package com.example.educationplatform.interceptor;

import com.example.educationplatform.dto.UserDTO;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.exception.BizException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");
        if (userDTO == null) {
            throw new BizException(ResultCode.NOT_LOGIN); // 未登录异常
        }
        return true; // 登录通过
    }


}
