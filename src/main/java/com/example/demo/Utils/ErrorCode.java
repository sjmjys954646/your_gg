package com.example.demo.Utils;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //Not Found
    USER_NOT_FOUND(NOT_FOUND, 1001, "사용자를 찾을 수 없습니다."),

    //API Error
    RIOT_API_ERROR(INTERNAL_SERVER_ERROR, 2001, "RIOT API 에러"),
    RIOT_API_ID_ERROR(NOT_FOUND, 2002, "존재하지 않는 아이디 입니다."),
    RIOT_API_NO_RECENTGAME(NOT_FOUND, 2003, "최근에 플레이한 게임이 없습니다."),

    //INVALID_DATA
    INVALID_DATA(BAD_REQUEST, 3001, "입력한 데이터가 유효하지 않습니다."),
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