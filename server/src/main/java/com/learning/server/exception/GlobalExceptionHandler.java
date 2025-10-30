package com.learning.server.exception;

import com.learning.server.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global Exception Handler
 * Xử lý tất cả các exception được throw từ controllers
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Tạo response entity lỗi
     * @param message Thông báo lỗi
     * @param status HTTP status code
     * @return ResponseEntity chứa ApiResponse lỗi
     */
    public static ResponseEntity<ApiResponse<?>> errorResponseEntity(String message, HttpStatus status) {
        ApiResponse<?> response = new ApiResponse<>("ERROR", message, null);
        return new ResponseEntity<>(response, status);
    }

    /**
     * Xử lý IllegalArgumentException
     * @param ex Exception
     * @return ResponseEntity với status BAD_REQUEST
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ApiResponse.error(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Xử lý NullPointerException
     * @param ex Exception
     * @return ResponseEntity với status INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse<?>> handleNullPointerException(NullPointerException ex) {
        return new ResponseEntity<>(
                ApiResponse.error(500, "Lỗi hệ thống: " + ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Xử lý RuntimeException chung
     * @param ex Exception
     * @return ResponseEntity với status INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(
                ApiResponse.error(500, "Lỗi không xác định: " + ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Xử lý Exception chung
     * @param ex Exception
     * @return ResponseEntity với status INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        return new ResponseEntity<>(
                ApiResponse.error(500, "Lỗi hệ thống: " + ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

