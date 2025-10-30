package com.learning.server.service.impl;

import com.learning.server.dto.NoteCreateDTO;
import com.learning.server.dto.NoteResponseDTO;
import com.learning.server.dto.NoteUpdateDTO;
import com.learning.server.entity.Note;
import com.learning.server.entity.User;
import com.learning.server.repository.NoteRepository;
import com.learning.server.repository.UserRepository;
import com.learning.server.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation của NoteService
 * Xử lý business logic cho Note
 */
@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Chuyển đổi Note entity sang NoteResponseDTO
     */
    private NoteResponseDTO convertToDTO(Note note) {
        return new NoteResponseDTO(
                note.getId(),
                note.getUser().getId(),
                note.getTitle(),
                note.getContent(),
                note.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public NoteResponseDTO createNote(UUID userId, NoteCreateDTO createDTO) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }
        if (createDTO == null) {
            throw new IllegalArgumentException("CreateDTO không được null");
        }

        // Tìm user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với ID: " + userId));

        // Tạo note mới
        Note note = new Note();
        note.setUser(user);
        note.setTitle(createDTO.title());
        note.setContent(createDTO.content());

        Note savedNote = noteRepository.save(note);
        return convertToDTO(savedNote);
    }

    @Override
    public List<NoteResponseDTO> getAllNotesByUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }

        // Verify user tồn tại
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với ID: " + userId));

        // Sử dụng query DTO trực tiếp từ repository
        return noteRepository.findNoteDTOsByUserId(userId);
    }

    @Override
    public NoteResponseDTO getNoteById(UUID id, UUID userId) {
        if (id == null) {
            throw new IllegalArgumentException("Note ID không được null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }

        // Sử dụng query DTO trực tiếp từ repository
        return noteRepository.findNoteDTOByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy note với ID: " + id + " hoặc bạn không có quyền truy cập"));
    }

    @Override
    @Transactional
    public NoteResponseDTO updateNote(UUID id, UUID userId, NoteUpdateDTO updateDTO) {
        if (id == null) {
            throw new IllegalArgumentException("Note ID không được null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }
        if (updateDTO == null) {
            throw new IllegalArgumentException("UpdateDTO không được null");
        }

        // Tìm note và verify ownership
        Note note = noteRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy note với ID: " + id + " hoặc bạn không có quyền truy cập"));

        // Cập nhật thông tin
        note.setTitle(updateDTO.title());
        note.setContent(updateDTO.content());

        Note updatedNote = noteRepository.save(note);
        return convertToDTO(updatedNote);
    }

    @Override
    @Transactional
    public void deleteNote(UUID id, UUID userId) {
        if (id == null) {
            throw new IllegalArgumentException("Note ID không được null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }

        // Tìm note và verify ownership
        Note note = noteRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy note với ID: " + id + " hoặc bạn không có quyền truy cập"));

        noteRepository.delete(note);
    }

    @Override
    public List<NoteResponseDTO> searchNotesByTitle(UUID userId, String title) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title không được để trống");
        }

        // Verify user tồn tại
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với ID: " + userId));

        // Tìm kiếm và convert sang DTO
        return noteRepository.findByUserIdAndTitleContaining(userId, title)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long countNotesByUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }

        // Verify user tồn tại
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với ID: " + userId));

        return noteRepository.countByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteAllNotesByUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }

        // Verify user tồn tại
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với ID: " + userId));

        noteRepository.deleteByUserId(userId);
    }
}
