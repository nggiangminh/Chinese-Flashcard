package com.learning.server.repository;

import com.learning.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository cho User entity
 * Xử lý các thao tác database liên quan đến User
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Tìm user theo email
     * @param email Email của user
     * @return Optional chứa User nếu tìm thấy
     */
    Optional<User> findByEmail(String email);

    /**
     * Kiểm tra user có tồn tại theo email không
     * @param email Email cần kiểm tra
     * @return true nếu tồn tại, false nếu không
     */
    boolean existsByEmail(String email);

    /**
     * Tìm user theo email và provider (Google OAuth)
     * @param email Email của user
     * @param provider Provider (vd: "google")
     * @return Optional chứa User nếu tìm thấy
     */
    Optional<User> findByEmailAndProvider(String email, String provider);
}
