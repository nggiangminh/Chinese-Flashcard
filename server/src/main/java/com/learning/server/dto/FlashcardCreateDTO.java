package com.learning.server.dto;

/**
 * DTO cho Flashcard Request (Create)
 */
public record FlashcardCreateDTO(
        String frontText,
        String backText
) {
    /**
     * Compact constructor để validate input
     */
    public FlashcardCreateDTO {
        if (frontText == null || frontText.isBlank()) {
            throw new IllegalArgumentException("Mặt trước không được để trống");
        }
        if (backText == null || backText.isBlank()) {
            throw new IllegalArgumentException("Mặt sau không được để trống");
        }
    }
}

