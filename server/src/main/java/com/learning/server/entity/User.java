package com.learning.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "Tên không được để trống")
    @Size(max = 100, message = "Tên không được vượt quá 100 ký tự")
    @Column(nullable = false, length = 100)
    private String name;

    @Email(message = "Email không hợp lệ")
    @NotEmpty(message = "Email không được để trống")
    @Column(unique = true, nullable = false)
    private String email;

    @NotEmpty(message = "Provider không được để trống")
    @Column(nullable = false, length = 50)
    private String provider;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
