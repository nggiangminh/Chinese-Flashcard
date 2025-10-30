package com.learning.server.service;

import com.learning.server.dto.FlashcardCreateDTO;
import com.learning.server.dto.FlashcardResponseDTO;
import com.learning.server.dto.FlashcardUpdateDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface cho Flashcard
 * Định nghĩa các business logic liên quan đến Flashcard
 */
public interface FlashcardService {

    /**
     * Tạo flashcard mới
     * @param userId UUID của user
     * @param createDTO DTO chứa thông tin flashcard
     * @return FlashcardResponseDTO
     */
    FlashcardResponseDTO createFlashcard(UUID userId, FlashcardCreateDTO createDTO);

    /**
     * Lấy tất cả flashcard của user
     * @param userId UUID của user
     * @return List FlashcardResponseDTO
     */
    List<FlashcardResponseDTO> getAllFlashcardsByUserId(UUID userId);

    /**
     * Lấy flashcard theo ID
     * @param id UUID của flashcard
     * @param userId UUID của user (để verify ownership)
     * @return FlashcardResponseDTO
     */
    FlashcardResponseDTO getFlashcardById(UUID id, UUID userId);

    /**
     * Cập nhật flashcard
     * @param id UUID của flashcard
     * @param userId UUID của user (để verify ownership)
     * @param updateDTO DTO chứa thông tin cập nhật
     * @return FlashcardResponseDTO
     */
    FlashcardResponseDTO updateFlashcard(UUID id, UUID userId, FlashcardUpdateDTO updateDTO);

    /**
     * Xóa flashcard
     * @param id UUID của flashcard
     * @param userId UUID của user (để verify ownership)
     */
    void deleteFlashcard(UUID id, UUID userId);

    /**
     * Đếm số lượng flashcard của user
     * @param userId UUID của user
     * @return Số lượng flashcard
     */
    long countFlashcardsByUserId(UUID userId);

    /**
     * Xóa tất cả flashcard của user
     * @param userId UUID của user
     */
    void deleteAllFlashcardsByUserId(UUID userId);
}

