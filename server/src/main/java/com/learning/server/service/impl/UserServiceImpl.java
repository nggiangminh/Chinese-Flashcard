package com.learning.server.service.impl;

import com.learning.server.dto.UserDTO;
import com.learning.server.entity.User;
import com.learning.server.repository.UserRepository;
import com.learning.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDTO findOrCreateUser(String email, String name) {
        return createOrGetUser(name, email, "google");
    }

    @Override
    @Transactional
    public UserDTO createOrGetUser(String name, String email, String provider) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên không được để trống");
        }
        if (provider == null || provider.trim().isEmpty()) {
            throw new IllegalArgumentException("Provider không được để trống");
        }

        // Tìm user theo email và provider
        return userRepository.findByEmailAndProvider(email, provider)
                .map(this::convertToDTO)
                .orElseGet(() -> {
                    // Tạo user mới nếu chưa tồn tại
                    User newUser = new User();
                    newUser.setName(name.trim());
                    newUser.setEmail(email.trim().toLowerCase());
                    newUser.setProvider(provider.trim().toLowerCase());

                    User savedUser = userRepository.save(newUser);
                    return convertToDTO(savedUser);
                });
    }

    @Override
    public UserDTO getUserById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID không được null");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với ID: " + id));
        return convertToDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }

        User user = userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với email: " + email));
        return convertToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return userRepository.existsByEmail(email.trim().toLowerCase());
    }

    @Override
    @Transactional
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        if (id == null) {
            throw new IllegalArgumentException("ID không được null");
        }
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO không được null");
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với ID: " + id));

        // Cập nhật thông tin
        if (userDTO.name() != null && !userDTO.name().trim().isEmpty()) {
            existingUser.setName(userDTO.name().trim());
        }
        if (userDTO.email() != null && !userDTO.email().trim().isEmpty()) {
            // Kiểm tra email mới có bị trùng không (trừ chính user hiện tại)
            String newEmail = userDTO.email().trim().toLowerCase();
            if (!existingUser.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)) {
                throw new IllegalArgumentException("Email đã được sử dụng bởi user khác");
            }
            existingUser.setEmail(newEmail);
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID không được null");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với ID: " + id));

        userRepository.delete(user);
    }

    /**
     * Chuyển đổi Entity sang DTO
     */
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getProvider(),
                user.getCreatedAt()
        );
    }
}
