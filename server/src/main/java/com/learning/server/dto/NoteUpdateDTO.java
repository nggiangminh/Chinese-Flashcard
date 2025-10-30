package com.learning.server.dto;

/**
 * DTO cho Note Request (Update)
 */
public record NoteUpdateDTO(
        String title,
        String content
) {
    /**
     * Compact constructor để validate input
     */
    public NoteUpdateDTO {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Tiêu đề không được để trống");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Nội dung không được để trống");
        }
    }
}

