
package com.learning.server.service;

import com.learning.server.dto.UserDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface cho User
 * Định nghĩa các business logic liên quan đến User
 */
public interface UserService {

    /**
     * Tạo user mới hoặc lấy user đã tồn tại (cho OAuth login)
     * @param name Tên user
     * @param email Email user
     * @param provider Provider (vd: "google")
     * @return UserDTO
     */
    UserDTO createOrGetUser(String name, String email, String provider);

    /**
     * Lấy user theo ID
     * @param id UUID của user
     * @return UserDTO
     */
    UserDTO getUserById(UUID id);

    /**
     * Lấy user theo email
     * @param email Email của user
     * @return UserDTO
     */
    UserDTO getUserByEmail(String email);

    /**
     * Lấy tất cả users
     * @return List UserDTO
     */
    List<UserDTO> getAllUsers();

    /**
     * Kiểm tra email đã tồn tại chưa
     * @param email Email cần kiểm tra
     * @return true nếu tồn tại, false nếu không
     */
    boolean existsByEmail(String email);

    /**
     * Xóa user theo ID
     * @param id UUID của user
     */
    void deleteUser(UUID id);
}

