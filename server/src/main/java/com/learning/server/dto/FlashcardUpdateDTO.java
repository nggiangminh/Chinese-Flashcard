package com.learning.server.dto;

/**
 * DTO cho Flashcard Request (Update)
 */
public record FlashcardUpdateDTO(
        String frontText,
        String backText
) {
    /**
     * Compact constructor để validate input
     */
    public FlashcardUpdateDTO {
        if (frontText == null || frontText.isBlank()) {
            throw new IllegalArgumentException("Mặt trước không được để trống");
        }
        if (backText == null || backText.isBlank()) {
            throw new IllegalArgumentException("Mặt sau không được để trống");
        }
    }
}

