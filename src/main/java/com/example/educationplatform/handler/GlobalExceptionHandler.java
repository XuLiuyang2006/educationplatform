package com.example.educationplatform.handler;

import com.example.educationplatform.common.Result;
import com.example.educationplatform.enums.ResultCode;
import com.example.educationplatform.exception.BizException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Result<Void> handleBizException(BizException e) {
        // 处理业务异常
        return Result.fail(e.getCode(), e.getMessage());
    }

    //TODO: 这个是用于处理参数校验异常的，具体是当使用@Validated注解进行参数校验时，如果参数不符合要求，会抛出MethodArgumentNotValidException异常
    //这个是用于处理参数校验异常的，具体是当使用@Valid注解进行参数校验时，如果参数不符合要求，会抛出MethodArgumentNotValidException异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidationException(MethodArgumentNotValidException ex) {
        return Result.fail(ResultCode.SERVER_ERROR, ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    //TODO: 这个是用于处理参数校验异常的，具体是当使用@Validated注解进行参数校验时，如果参数不符合要求，会抛出ConstraintViolationException异常
    //这个是用于处理参数校验异常的，具体是当使用@Valid注解进行参数校验时，如果参数不符合要求，会抛出ConstraintViolationException异常
//    @ExceptionHandler(ConstraintViolationException.class)
//    public Result<Void> handleParamValidationException(ConstdraintViolationException ex) {
//        return Result.fail(ResultCode.SERVER_ERROR, ex.getMessage());
//    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleOtherException(Exception ex) {
        return Result.fail(ResultCode.SERVER_ERROR, "系统异常: " + ex.getMessage());
    }

}
