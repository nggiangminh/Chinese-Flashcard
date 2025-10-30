package com.learning.server.repository;

import com.learning.server.dto.FlashcardResponseDTO;
import com.learning.server.entity.Flashcard;
import com.learning.server.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository cho Flashcard entity
 * Xử lý các thao tác database liên quan đến Flashcard
 */
@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, UUID> {

    /**
     * Tìm tất cả flashcard của một user
     * Sử dụng EntityGraph để tránh N+1 problem
     * @param user User entity
     * @return List các Flashcard của user
     */
    @EntityGraph(attributePaths = {"user"})
    List<Flashcard> findByUser(User user);

    /**
     * Tìm tất cả flashcard của user theo userId
     * Sử dụng EntityGraph để tránh N+1 problem
     * @param userId UUID của user
     * @return List các Flashcard của user
     */
    @EntityGraph(attributePaths = {"user"})
    List<Flashcard> findByUserId(UUID userId);

    /**
     * Tìm flashcard theo ID và user (để đảm bảo user chỉ truy cập flashcard của mình)
     * @param id UUID của flashcard
     * @param user User entity
     * @return Optional chứa Flashcard nếu tìm thấy
     */
    Optional<Flashcard> findByIdAndUser(UUID id, User user);

    /**
     * Tìm flashcard theo ID và userId
     * @param id UUID của flashcard
     * @param userId UUID của user
     * @return Optional chứa Flashcard nếu tìm thấy
     */
    Optional<Flashcard> findByIdAndUserId(UUID id, UUID userId);

    /**
     * Lấy danh sách flashcard của user dưới dạng DTO
     * Sử dụng JPQL để query và map trực tiếp sang DTO
     * @param userId UUID của user
     * @return List các FlashcardResponseDTO
     */
    @Query("""
            SELECT new com.learning.server.dto.FlashcardResponseDTO(
                f.id,
                f.user.id,
                f.frontText,
                f.backText,
                f.createdAt,
                f.updatedAt
            )
            FROM Flashcard f
            WHERE f.user.id = :userId
            ORDER BY f.createdAt DESC
            """)
    List<FlashcardResponseDTO> findFlashcardDTOsByUserId(@Param("userId") UUID userId);

    /**
     * Lấy flashcard theo ID dưới dạng DTO
     * @param id UUID của flashcard
     * @param userId UUID của user
     * @return Optional chứa FlashcardResponseDTO nếu tìm thấy
     */
    @Query("""
            SELECT new com.learning.server.dto.FlashcardResponseDTO(
                f.id,
                f.user.id,
                f.frontText,
                f.backText,
                f.createdAt,
                f.updatedAt
            )
            FROM Flashcard f
            WHERE f.id = :id AND f.user.id = :userId
            """)
    Optional<FlashcardResponseDTO> findFlashcardDTOByIdAndUserId(
            @Param("id") UUID id,
            @Param("userId") UUID userId
    );

    /**
     * Đếm số lượng flashcard của user
     * @param userId UUID của user
     * @return Số lượng flashcard
     */
    long countByUserId(UUID userId);

    /**
     * Xóa tất cả flashcard của user
     * @param userId UUID của user
     */
    void deleteByUserId(UUID userId);
}

