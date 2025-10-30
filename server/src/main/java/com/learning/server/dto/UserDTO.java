package com.learning.server.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO cho User Response
 */
public record UserDTO(
        UUID id,
        String name,
        String email,
        String provider,
        LocalDateTime createdAt
) {
    /**
     * Compact constructor để validate input
     */
    public UserDTO {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tên không được để trống");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        if (provider == null || provider.isBlank()) {
            throw new IllegalArgumentException("Provider không được để trống");
        }
    }
}

