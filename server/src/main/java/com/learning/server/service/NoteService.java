package com.learning.server.service;

import com.learning.server.dto.NoteCreateDTO;
import com.learning.server.dto.NoteResponseDTO;
import com.learning.server.dto.NoteUpdateDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface cho Note
 * Định nghĩa các business logic liên quan đến Note
 */
public interface NoteService {

    /**
     * Tạo note mới
     * @param userId UUID của user
     * @param createDTO DTO chứa thông tin note
     * @return NoteResponseDTO
     */
    NoteResponseDTO createNote(UUID userId, NoteCreateDTO createDTO);

    /**
     * Lấy tất cả note của user
     * @param userId UUID của user
     * @return List NoteResponseDTO
     */
    List<NoteResponseDTO> getAllNotesByUserId(UUID userId);

    /**
     * Lấy note theo ID
     * @param id UUID của note
     * @param userId UUID của user (để verify ownership)
     * @return NoteResponseDTO
     */
    NoteResponseDTO getNoteById(UUID id, UUID userId);

    /**
     * Cập nhật note
     * @param id UUID của note
     * @param userId UUID của user (để verify ownership)
     * @param updateDTO DTO chứa thông tin cập nhật
     * @return NoteResponseDTO
     */
    NoteResponseDTO updateNote(UUID id, UUID userId, NoteUpdateDTO updateDTO);

    /**
     * Xóa note
     * @param id UUID của note
     * @param userId UUID của user (để verify ownership)
     */
    void deleteNote(UUID id, UUID userId);

    /**
     * Tìm kiếm note theo title
     * @param userId UUID của user
     * @param title Từ khóa tìm kiếm
     * @return List NoteResponseDTO
     */
    List<NoteResponseDTO> searchNotesByTitle(UUID userId, String title);

    /**
     * Đếm số lượng note của user
     * @param userId UUID của user
     * @return Số lượng note
     */
    long countNotesByUserId(UUID userId);

    /**
     * Xóa tất cả note của user
     * @param userId UUID của user
     */
    void deleteAllNotesByUserId(UUID userId);
}