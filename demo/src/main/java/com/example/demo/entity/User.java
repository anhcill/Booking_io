package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// Đây là class đại diện cho bảng "users" trong database
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    // Khóa chính, tự động tăng
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Họ tên đầy đủ
    @Column(name = "full_name", nullable = false)
    private String fullName;

    // Email dùng để đăng nhập, phải là duy nhất
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Mật khẩu đã được mã hóa bằng BCrypt
    @Column(name = "password", nullable = false)
    private String password;

    // Vai trò: USER hoặc ADMIN
    @Column(name = "role", nullable = false)
    private String role = "USER";

    // Thời gian tạo tài khoản
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Thời gian cập nhật gần nhất
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Tự động set thời gian khi tạo mới
    @PrePersist
    public void beforeCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Tự động cập nhật thời gian khi sửa
    @PreUpdate
    public void beforeUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
