package com.example.demo.Utils;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
    //Not Found
    USER_NOT_FOUND(NOT_FOUND, 1001, "사용자를 찾을 수 없습니다."),

    //API Error
    RIOD_API_ERROR(INTERNAL_SERVER_ERROR, 2001, "RIOT API 에러"),
    RIOT_API_ID_ERROR(NOT_FOUND, 2002, "RIOT API 아이디가 없음")

    ;

    private final HttpStatus status;
    private final int code;
    private final String message;

    ErrorCode(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}