package com.learning.server.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API Response wrapper cho tất cả các response từ API
 * @param <T> Kiểu dữ liệu trả về
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private String result;    // SUCCESS hoặc ERROR
    private String message;   // Thông báo thành công hoặc lỗi
    private T data;           // Dữ liệu trả về từ service class (nếu thành công)

    /**
     * Tạo response thành công
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("SUCCESS", message, data);
    }

    /**
     * Tạo response thành công không có data
     */
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>("SUCCESS", message, null);
    }

    /**
     * Tạo response lỗi
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("ERROR", message, null);
    }

    /**
     * Tạo response lỗi với status code
     */
    public static <T> ApiResponse<T> error(int statusCode, String message) {
        return new ApiResponse<>("ERROR", "[" + statusCode + "] " + message, null);
    }
}

