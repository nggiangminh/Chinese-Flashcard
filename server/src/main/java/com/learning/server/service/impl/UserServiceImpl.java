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
import java.util.stream.Collectors;

/**
 * Implementation của UserService
 * Xử lý business logic cho User
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Chuyển đổi User entity sang UserDTO
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

    @Override
    @Transactional
    public UserDTO createOrGetUser(String name, String email, String provider) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tên không được để trống");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        if (provider == null || provider.isBlank()) {
            throw new IllegalArgumentException("Provider không được để trống");
        }

        // Kiểm tra user đã tồn tại chưa
        return userRepository.findByEmailAndProvider(email, provider)
                .map(this::convertToDTO)
                .orElseGet(() -> {
                    // Tạo user mới
                    User newUser = new User();
                    newUser.setName(name);
                    newUser.setEmail(email);
                    newUser.setProvider(provider);
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
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email không được để trống");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với email: " + email));
        return convertToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        return userRepository.existsByEmail(email);
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
}

