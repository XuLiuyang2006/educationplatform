package com.example.educationplatform.exception;

import com.example.educationplatform.enums.ResultCode;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

    private final ResultCode code;

    public BizException(ResultCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public BizException(ResultCode code, String message) {
        super(message);
        this.code = code;
    }
}
