package com.example.demo.Utils;

import com.example.demo.Utils.Exceptions.BadRequestException;
import com.example.demo.Utils.Exceptions.ForbiddenException;
import com.example.demo.Utils.Exceptions.InternalServerError;
import com.example.demo.Utils.Exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    //403 Forbidden
    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<ApiResponse<Object>> handleForbiddenException(
            ForbiddenException exception,
            HttpServletRequest request) {
        logInfo(request, FORBIDDEN, exception.getMessage());
        return  ResponseEntity.status(FORBIDDEN).body(ApiResponse.error(exception.getCode()));
    }

    //404 Not Found
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ApiResponse<Object>> handleNotFoundException(
            NotFoundException exception,
            HttpServletRequest request) {
        logInfo(request, NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(ApiResponse.error(exception.getCode()));
    }


    // 500 Internal Error
    @ExceptionHandler(InternalServerError.class)
    protected ResponseEntity<ApiResponse<Object>> handleInternalServerError(
            InternalServerError exception,
            HttpServletRequest request) {
        logInfo(request, INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.error(exception.getCode()));
    }

    private void logInfo(HttpServletRequest request, HttpStatus status, String message) {
        String logMessage = String.format("%s %s : %s - %s", request.getMethod(), request.getRequestURI(), status,
                message);
        logger.info(logMessage);
    }
}
