package com.learning.server.service.impl;

import com.learning.server.dto.FlashcardCreateDTO;
import com.learning.server.dto.FlashcardResponseDTO;
import com.learning.server.dto.FlashcardUpdateDTO;
import com.learning.server.entity.Flashcard;
import com.learning.server.entity.User;
import com.learning.server.repository.FlashcardRepository;
import com.learning.server.repository.UserRepository;
import com.learning.server.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation của FlashcardService
 * Xử lý business logic cho Flashcard
 */
@Service
public class FlashcardServiceImpl implements FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Chuyển đổi Flashcard entity sang FlashcardResponseDTO
     */
    private FlashcardResponseDTO convertToDTO(Flashcard flashcard) {
        return new FlashcardResponseDTO(
                flashcard.getId(),
                flashcard.getUser().getId(),
                flashcard.getFrontText(),
                flashcard.getBackText(),
                flashcard.getCreatedAt(),
                flashcard.getUpdatedAt()
        );
    }

    @Override
    @Transactional
    public FlashcardResponseDTO createFlashcard(UUID userId, FlashcardCreateDTO createDTO) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }
        if (createDTO == null) {
            throw new IllegalArgumentException("CreateDTO không được null");
        }

        // Tìm user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với ID: " + userId));

        // Tạo flashcard mới
        Flashcard flashcard = new Flashcard();
        flashcard.setUser(user);
        flashcard.setFrontText(createDTO.frontText());
        flashcard.setBackText(createDTO.backText());

        Flashcard savedFlashcard = flashcardRepository.save(flashcard);
        return convertToDTO(savedFlashcard);
    }

    @Override
    public List<FlashcardResponseDTO> getAllFlashcardsByUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }

        // Verify user tồn tại
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với ID: " + userId));

        // Sử dụng query DTO trực tiếp từ repository
        return flashcardRepository.findFlashcardDTOsByUserId(userId);
    }

    @Override
    public FlashcardResponseDTO getFlashcardById(UUID id, UUID userId) {
        if (id == null) {
            throw new IllegalArgumentException("Flashcard ID không được null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }

        // Sử dụng query DTO trực tiếp từ repository
        return flashcardRepository.findFlashcardDTOByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy flashcard với ID: " + id + " hoặc bạn không có quyền truy cập"));
    }

    @Override
    @Transactional
    public FlashcardResponseDTO updateFlashcard(UUID id, UUID userId, FlashcardUpdateDTO updateDTO) {
        if (id == null) {
            throw new IllegalArgumentException("Flashcard ID không được null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }
        if (updateDTO == null) {
            throw new IllegalArgumentException("UpdateDTO không được null");
        }

        // Tìm flashcard và verify ownership
        Flashcard flashcard = flashcardRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy flashcard với ID: " + id + " hoặc bạn không có quyền truy cập"));

        // Cập nhật thông tin
        flashcard.setFrontText(updateDTO.frontText());
        flashcard.setBackText(updateDTO.backText());

        Flashcard updatedFlashcard = flashcardRepository.save(flashcard);
        return convertToDTO(updatedFlashcard);
    }

    @Override
    @Transactional
    public void deleteFlashcard(UUID id, UUID userId) {
        if (id == null) {
            throw new IllegalArgumentException("Flashcard ID không được null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }

        // Tìm flashcard và verify ownership
        Flashcard flashcard = flashcardRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy flashcard với ID: " + id + " hoặc bạn không có quyền truy cập"));

        flashcardRepository.delete(flashcard);
    }

    @Override
    public long countFlashcardsByUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }

        // Verify user tồn tại
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với ID: " + userId));

        return flashcardRepository.countByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteAllFlashcardsByUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }

        // Verify user tồn tại
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với ID: " + userId));

        flashcardRepository.deleteByUserId(userId);
    }
}

