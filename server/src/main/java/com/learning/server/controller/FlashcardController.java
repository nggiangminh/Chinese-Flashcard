package com.learning.server.controller;

import com.learning.server.dto.FlashcardCreateDTO;
import com.learning.server.dto.FlashcardResponseDTO;
import com.learning.server.dto.FlashcardUpdateDTO;
import com.learning.server.response.ApiResponse;
import com.learning.server.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * RestController cho Flashcard
 * Xử lý các HTTP requests liên quan đến Flashcard
 */
@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;

    /**
     * Tạo flashcard mới
     * POST /api/flashcards?userId={userId}
     */
    @PostMapping
    public ResponseEntity<ApiResponse<FlashcardResponseDTO>> createFlashcard(
            @RequestParam UUID userId,
            @RequestBody FlashcardCreateDTO createDTO) {
        try {
            FlashcardResponseDTO flashcard = flashcardService.createFlashcard(userId, createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.success("Tạo flashcard thành công", flashcard)
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(ex.getMessage())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.error("Lỗi hệ thống: " + ex.getMessage())
            );
        }
    }

    /**
     * Lấy tất cả flashcard của user
     * GET /api/flashcards?userId={userId}
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<FlashcardResponseDTO>>> getAllFlashcards(
            @RequestParam UUID userId) {
        try {
            List<FlashcardResponseDTO> flashcards = flashcardService.getAllFlashcardsByUserId(userId);
            return ResponseEntity.ok(
                    ApiResponse.success("Lấy danh sách flashcard thành công", flashcards)
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(ex.getMessage())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.error("Lỗi hệ thống: " + ex.getMessage())
            );
        }
    }

    /**
     * Lấy flashcard theo ID
     * GET /api/flashcards/{id}?userId={userId}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FlashcardResponseDTO>> getFlashcardById(
            @PathVariable UUID id,
            @RequestParam UUID userId) {
        try {
            FlashcardResponseDTO flashcard = flashcardService.getFlashcardById(id, userId);
            return ResponseEntity.ok(
                    ApiResponse.success("Lấy flashcard thành công", flashcard)
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(ex.getMessage())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.error("Lỗi hệ thống: " + ex.getMessage())
            );
        }
    }

    /**
     * Cập nhật flashcard
     * PUT /api/flashcards/{id}?userId={userId}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FlashcardResponseDTO>> updateFlashcard(
            @PathVariable UUID id,
            @RequestParam UUID userId,
            @RequestBody FlashcardUpdateDTO updateDTO) {
        try {
            FlashcardResponseDTO flashcard = flashcardService.updateFlashcard(id, userId, updateDTO);
            return ResponseEntity.ok(
                    ApiResponse.success("Cập nhật flashcard thành công", flashcard)
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(ex.getMessage())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.error("Lỗi hệ thống: " + ex.getMessage())
            );
        }
    }

    /**
     * Xóa flashcard
     * DELETE /api/flashcards/{id}?userId={userId}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFlashcard(
            @PathVariable UUID id,
            @RequestParam UUID userId) {
        try {
            flashcardService.deleteFlashcard(id, userId);
            return ResponseEntity.ok(
                    ApiResponse.success("Xóa flashcard thành công")
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(ex.getMessage())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.error("Lỗi hệ thống: " + ex.getMessage())
            );
        }
    }

    /**
     * Đếm số lượng flashcard của user
     * GET /api/flashcards/count?userId={userId}
     */
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> countFlashcards(@RequestParam UUID userId) {
        try {
            long count = flashcardService.countFlashcardsByUserId(userId);
            return ResponseEntity.ok(
                    ApiResponse.success("Đếm flashcard thành công", count)
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(ex.getMessage())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.error("Lỗi hệ thống: " + ex.getMessage())
            );
        }
    }

    /**
     * Xóa tất cả flashcard của user
     * DELETE /api/flashcards/all?userId={userId}
     */
    @DeleteMapping("/all")
    public ResponseEntity<ApiResponse<Void>> deleteAllFlashcards(@RequestParam UUID userId) {
        try {
            flashcardService.deleteAllFlashcardsByUserId(userId);
            return ResponseEntity.ok(
                    ApiResponse.success("Xóa tất cả flashcard thành công")
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(ex.getMessage())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.error("Lỗi hệ thống: " + ex.getMessage())
            );
        }
    }
}

