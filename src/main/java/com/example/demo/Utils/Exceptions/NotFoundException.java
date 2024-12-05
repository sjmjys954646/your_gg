package com.example.demo.Utils.Exceptions;

import com.example.demo.Utils.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final ErrorCode code;

    public NotFoundException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
