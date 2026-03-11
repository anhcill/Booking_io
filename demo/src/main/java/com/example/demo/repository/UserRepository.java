package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repository giúp mình thao tác với bảng users trong database
// Spring tự generate các hàm CRUD cơ bản, mình chỉ cần thêm những hàm đặc biệt
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Tìm user theo email — dùng khi đăng nhập
    Optional<User> findByEmail(String email);

    // Kiểm tra email đã có trong DB chưa — dùng khi đăng ký
    boolean existsByEmail(String email);
}
