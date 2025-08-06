package com.example.educationplatform.common;

import com.example.educationplatform.enums.ResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "通用响应结果类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    //有参构造器
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    //无参构造器
    public static Result<Void> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    //失败的构造器
    public static Result<Void> fail(ResultCode code) {
        return new Result<>(code.getCode(), code.getMessage(), null);
    }

    // 失败的构造器，带自定义消息
    public static Result<Void> fail(ResultCode code, String msg) {
        return new Result<>(code.getCode(), msg, null);
    }

    // 失败的构造器，带自定义状态码和消息
    public static Result<Void> fail(int code, String msg) {
        return new Result<>(code, msg, null);
    }
}
