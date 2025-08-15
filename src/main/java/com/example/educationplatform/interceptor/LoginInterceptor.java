package com.example.educationplatform.interceptor;

import com.example.educationplatform.dto.UserDTO;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.exception.BizException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
////        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");
////        if (userDTO == null) {
////            throw new BizException(ResultCode.NOT_LOGIN); // 未登录异常
////        }

//        HttpSession session = request.getSession(false); // false 表示不创建新Session
//        if (session == null || session.getAttribute("userId") == null) {
//            throw new BizException(ResultCode.NOT_LOGIN);// 未登录异常
//        }
        // 跳过静态资源处理器
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethod().isAnnotationPresent(com.example.educationplatform.annotation.LoginRequired.class)) {
            //这里可以使用，hasMethodAnnotation()方法来判断是否有LoginRequired注解
            //if (handlerMethod.hasMethodAnnotation(LoginRequired.class))

            HttpSession session = request.getSession(false); // false 表示不创建新Session
            if (session == null || session.getAttribute("userId") == null) {
                throw new BizException(ResultCode.NOT_LOGIN);// 未登录异常
            }
        }

        return true; // 登录通过
    }


}
