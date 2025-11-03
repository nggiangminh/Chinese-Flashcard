package com.learning.server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(
        UUID id,
        @NotNull(message = "Tên không được null")
        @NotBlank(message = "Tên không được để trống")
        String name,

        @NotNull(message = "Email không được null")
        @NotBlank(message = "Email không được để trống")
        @Email(message = "Email không hợp lệ")
        String email,

        @NotNull(message = "Provider không được null")
        @NotBlank(message = "Provider không được để trống")
        String provider,

        LocalDateTime createdAt
) {
    public UserDTO {
        if (name != null && (name.isBlank() || name.trim().isEmpty())) {
            throw new IllegalArgumentException("Tên không được để trống");
        }
        if (email != null && (email.isBlank() || email.trim().isEmpty())) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        if (provider != null && (provider.isBlank() || provider.trim().isEmpty())) {
            throw new IllegalArgumentException("Provider không được để trống");
        }
    }
}
