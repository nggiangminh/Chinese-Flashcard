package com.learning.server.repository;

import com.learning.server.dto.NoteResponseDTO;
import com.learning.server.entity.Note;
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
 * Repository cho Note entity
 * Xử lý các thao tác database liên quan đến Note
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {

    /**
     * Tìm tất cả note của một user
     * Sử dụng EntityGraph để tránh N+1 problem
     * @param user User entity
     * @return List các Note của user
     */
    @EntityGraph(attributePaths = {"user"})
    List<Note> findByUser(User user);

    /**
     * Tìm tất cả note của user theo userId
     * Sử dụng EntityGraph để tránh N+1 problem
     * @param userId UUID của user
     * @return List các Note của user
     */
    @EntityGraph(attributePaths = {"user"})
    List<Note> findByUserId(UUID userId);

    /**
     * Tìm note theo ID và user (để đảm bảo user chỉ truy cập note của mình)
     * @param id UUID của note
     * @param user User entity
     * @return Optional chứa Note nếu tìm thấy
     */
    Optional<Note> findByIdAndUser(UUID id, User user);

    /**
     * Tìm note theo ID và userId
     * @param id UUID của note
     * @param userId UUID của user
     * @return Optional chứa Note nếu tìm thấy
     */
    Optional<Note> findByIdAndUserId(UUID id, UUID userId);

    /**
     * Lấy danh sách note của user dưới dạng DTO
     * Sử dụng JPQL để query và map trực tiếp sang DTO
     * @param userId UUID của user
     * @return List các NoteResponseDTO
     */
    @Query("""
            SELECT new com.learning.server.dto.NoteResponseDTO(
                n.id,
                n.user.id,
                n.title,
                n.content,
                n.createdAt
            )
            FROM Note n
            WHERE n.user.id = :userId
            ORDER BY n.createdAt DESC
            """)
    List<NoteResponseDTO> findNoteDTOsByUserId(@Param("userId") UUID userId);

    /**
     * Lấy note theo ID dưới dạng DTO
     * @param id UUID của note
     * @param userId UUID của user
     * @return Optional chứa NoteResponseDTO nếu tìm thấy
     */
    @Query("""
            SELECT new com.learning.server.dto.NoteResponseDTO(
                n.id,
                n.user.id,
                n.title,
                n.content,
                n.createdAt
            )
            FROM Note n
            WHERE n.id = :id AND n.user.id = :userId
            """)
    Optional<NoteResponseDTO> findNoteDTOByIdAndUserId(
            @Param("id") UUID id, 
            @Param("userId") UUID userId
    );

    /**
     * Tìm note theo title (tìm kiếm)
     * @param userId UUID của user
     * @param title Tiêu đề cần tìm (chứa chuỗi)
     * @return List các Note khớp với tiêu đề
     */
    @Query("SELECT n FROM Note n WHERE n.user.id = :userId AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Note> findByUserIdAndTitleContaining(@Param("userId") UUID userId, @Param("title") String title);

    /**
     * Đếm số lượng note của user
     * @param userId UUID của user
     * @return Số lượng note
     */
    long countByUserId(UUID userId);

    /**
     * Xóa tất cả note của user
     * @param userId UUID của user
     */
    void deleteByUserId(UUID userId);
}

