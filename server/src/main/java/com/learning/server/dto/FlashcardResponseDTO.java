package com.learning.server.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO cho Flashcard Response
 */
public record FlashcardResponseDTO(
        UUID id,
        UUID userId,
        String frontText,
        String backText,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    /**
     * Compact constructor để validate input
     */
    public FlashcardResponseDTO {
        if (frontText == null || frontText.isBlank()) {
            throw new IllegalArgumentException("Mặt trước không được để trống");
        }
        if (backText == null || backText.isBlank()) {
            throw new IllegalArgumentException("Mặt sau không được để trống");
        }
    }
}

