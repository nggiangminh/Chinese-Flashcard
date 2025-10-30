package com.learning.server.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO cho Note Response
 */
public record NoteResponseDTO(
        UUID id,
        UUID userId,
        String title,
        String content,
        LocalDateTime createdAt
) {
    /**
     * Compact constructor để validate input
     */
    public NoteResponseDTO {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Tiêu đề không được để trống");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Nội dung không được để trống");
        }
    }
}

