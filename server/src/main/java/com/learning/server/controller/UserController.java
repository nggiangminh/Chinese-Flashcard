package com.learning.server.controller;

import com.learning.server.dto.UserDTO;
import com.learning.server.response.ApiResponse;
import com.learning.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * RestController cho User
 * Xử lý các HTTP requests liên quan đến User
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Tạo user mới hoặc lấy user đã tồn tại (cho OAuth login)
     * POST /api/users/auth
     */
    @PostMapping("/auth")
    public ResponseEntity<ApiResponse<UserDTO>> createOrGetUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String provider) {
        try {
            UserDTO userDTO = userService.createOrGetUser(name, email, provider);
            return ResponseEntity.ok(
                    ApiResponse.success("Đăng nhập thành công", userDTO)
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(ex.getMessage())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.error("Lỗi hệ thống: " + ex.getMessage())
            );
        }
    }

    /**
     * Lấy user theo ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable UUID id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            return ResponseEntity.ok(
                    ApiResponse.success("Lấy thông tin user thành công", userDTO)
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(ex.getMessage())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.error("Lỗi hệ thống: " + ex.getMessage())
            );
        }
    }

    /**
     * Lấy user theo email
     * GET /api/users/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByEmail(@PathVariable String email) {
        try {
            UserDTO userDTO = userService.getUserByEmail(email);
            return ResponseEntity.ok(
                    ApiResponse.success("Lấy thông tin user thành công", userDTO)
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(ex.getMessage())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.error("Lỗi hệ thống: " + ex.getMessage())
            );
        }
    }

    /**
     * Lấy tất cả users
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            return ResponseEntity.ok(
                    ApiResponse.success("Lấy danh sách users thành công", users)
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.error("Lỗi hệ thống: " + ex.getMessage())
            );
        }
    }

    /**
     * Kiểm tra email đã tồn tại chưa
     * GET /api/users/exists?email={email}
     */
    @GetMapping("/exists")
    public ResponseEntity<ApiResponse<Boolean>> existsByEmail(@RequestParam String email) {
        try {
            boolean exists = userService.existsByEmail(email);
            return ResponseEntity.ok(
                    ApiResponse.success("Kiểm tra email thành công", exists)
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(ex.getMessage())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.error("Lỗi hệ thống: " + ex.getMessage())
            );
        }
    }

    /**
     * Xóa user
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(
                    ApiResponse.success("Xóa user thành công")
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(ex.getMessage())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.error("Lỗi hệ thống: " + ex.getMessage())
            );
        }
    }
}

