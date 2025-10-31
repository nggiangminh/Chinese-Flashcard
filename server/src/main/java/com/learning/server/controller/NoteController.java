package com.learning.server.controller;

import com.learning.server.dto.NoteCreateDTO;
import com.learning.server.dto.NoteResponseDTO;
import com.learning.server.dto.NoteUpdateDTO;
import com.learning.server.response.ApiResponse;
import com.learning.server.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * RestController cho Note
 * Xử lý các HTTP requests liên quan đến Note
 */
@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    /**
     * Tạo note mới
     * POST /api/notes?userId={userId}
     */
    @PostMapping
    public ResponseEntity<ApiResponse<NoteResponseDTO>> createNote(
            @RequestParam UUID userId,
            @RequestBody NoteCreateDTO createDTO) {
        try {
            NoteResponseDTO note = noteService.createNote(userId, createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.success("Tạo note thành công", note)
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
     * Lấy tất cả note của user
     * GET /api/notes?userId={userId}
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<NoteResponseDTO>>> getAllNotes(
            @RequestParam UUID userId) {
        try {
            List<NoteResponseDTO> notes = noteService.getAllNotesByUserId(userId);
            return ResponseEntity.ok(
                    ApiResponse.success("Lấy danh sách note thành công", notes)
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
     * Lấy note theo ID
     * GET /api/notes/{id}?userId={userId}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteResponseDTO>> getNoteById(
            @PathVariable UUID id,
            @RequestParam UUID userId) {
        try {
            NoteResponseDTO note = noteService.getNoteById(id, userId);
            return ResponseEntity.ok(
                    ApiResponse.success("Lấy note thành công", note)
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
     * Cập nhật note
     * PUT /api/notes/{id}?userId={userId}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteResponseDTO>> updateNote(
            @PathVariable UUID id,
            @RequestParam UUID userId,
            @RequestBody NoteUpdateDTO updateDTO) {
        try {
            NoteResponseDTO note = noteService.updateNote(id, userId, updateDTO);
            return ResponseEntity.ok(
                    ApiResponse.success("Cập nhật note thành công", note)
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
     * Xóa note
     * DELETE /api/notes/{id}?userId={userId}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNote(
            @PathVariable UUID id,
            @RequestParam UUID userId) {
        try {
            noteService.deleteNote(id, userId);
            return ResponseEntity.ok(
                    ApiResponse.success("Xóa note thành công")
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
     * Tìm kiếm note theo title
     * GET /api/notes/search?userId={userId}&title={title}
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<NoteResponseDTO>>> searchNotes(
            @RequestParam UUID userId,
            @RequestParam String title) {
        try {
            List<NoteResponseDTO> notes = noteService.searchNotesByTitle(userId, title);
            return ResponseEntity.ok(
                    ApiResponse.success("Tìm kiếm note thành công", notes)
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
     * Đếm số lượng note của user
     * GET /api/notes/count?userId={userId}
     */
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> countNotes(@RequestParam UUID userId) {
        try {
            long count = noteService.countNotesByUserId(userId);
            return ResponseEntity.ok(
                    ApiResponse.success("Đếm note thành công", count)
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
     * Xóa tất cả note của user
     * DELETE /api/notes/all?userId={userId}
     */
    @DeleteMapping("/all")
    public ResponseEntity<ApiResponse<Void>> deleteAllNotes(@RequestParam UUID userId) {
        try {
            noteService.deleteAllNotesByUserId(userId);
            return ResponseEntity.ok(
                    ApiResponse.success("Xóa tất cả note thành công")
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

