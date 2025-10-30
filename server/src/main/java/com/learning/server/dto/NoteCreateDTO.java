package com.learning.server.dto;

/**
 * DTO cho Note Request (Create)
 */
public record NoteCreateDTO(
        String title,
        String content
) {
    /**
     * Compact constructor để validate input
     */
    public NoteCreateDTO {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Tiêu đề không được để trống");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Nội dung không được để trống");
        }
    }
}

