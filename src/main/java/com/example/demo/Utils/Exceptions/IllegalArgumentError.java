package com.example.demo.Utils.Exceptions;

import com.example.demo.Utils.ErrorCode;
import lombok.Getter;

@Getter
public class IllegalArgumentError extends RuntimeException {
    private final ErrorCode code;

    public IllegalArgumentError(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
