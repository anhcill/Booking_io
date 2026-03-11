package com.example.demo.service.impl;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.RegisterResponse;
import com.example.demo.dto.UpdateProfileRequest;
import com.example.demo.dto.UpdateProfileResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.FieldValidationException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Class thực thi logic của AuthService
// Toàn bộ xử lý nghiệp vụ và kiểm tra lỗi đều nằm ở đây
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ============================================================
    // ĐĂNG NHẬP
    // ============================================================
    @Override
    public LoginResponse login(LoginRequest request) {
        // Bước 1: Tìm user theo email trong database
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        // Bước 2: Nếu không tìm thấy user → throw UnauthorizedException → 401
        if (user == null) {
            throw new UnauthorizedException("Invalid email or password");
        }

        // Bước 3: So sánh mật khẩu user nhập với mật khẩu đã mã hóa trong DB
        boolean isPasswordCorrect = passwordEncoder.matches(
                request.getPassword(), // Mật khẩu thô từ request
                user.getPassword()     // Mật khẩu đã BCrypt trong DB
        );

        // Bước 4: Nếu mật khẩu sai → throw UnauthorizedException → 401
        if (!isPasswordCorrect) {
            throw new UnauthorizedException("Invalid email or password");
        }

        // Bước 5: Tạo JWT token từ email và role của user
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        // Bước 6: Lấy thời gian hết hạn và trả về response
        String expireTime = jwtUtil.getExpireTime();

        return new LoginResponse(token, expireTime);
    }

    // ============================================================
    // ĐĂNG KÝ
    // ============================================================
    @Override
    public RegisterResponse register(RegisterRequest request) {
        // Bước 1: Kiểm tra email đã có ai đăng ký chưa
        boolean emailExists = userRepository.existsByEmail(request.getEmail());

        // Nếu email đã tồn tại → throw FieldValidationException → 422
        if (emailExists) {
            throw new FieldValidationException("email", "Email is already registered");
        }

        // Bước 2: Tạo object User mới và mã hóa mật khẩu bằng BCrypt
        User newUser = new User();
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword())); // Mã hóa mật khẩu!
        newUser.setRole("USER");

        // Bước 3: Lưu user vào database → Hibernate tự gán id cho mình
        User savedUser = userRepository.save(newUser);

        // Bước 4: Trả về thông tin user vừa tạo (không trả về password!)
        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail());
    }

    // ============================================================
    // CẬP NHẬT THÔNG TIN CÁ NHÂN
    // ============================================================
    @Override
    public UpdateProfileResponse updateProfile(String email, UpdateProfileRequest request) {
        // Bước 1: Tìm user theo email (email lấy từ JWT token — không phải client tự gửi)
        User user = userRepository.findByEmail(email)
                .orElse(null);

        // Bước 2: Nếu không tìm thấy user (trường hợp hiếm) → throw 401
        if (user == null) {
            throw new UnauthorizedException("User not found");
        }

        // Bước 3: Cập nhật họ tên mới
        user.setFullName(request.getFullName());

        // Bước 4: Lưu lại vào database (@PreUpdate tự cập nhật updated_at)
        User updatedUser = userRepository.save(user);

        // Bước 5: Trả về thông tin sau khi cập nhập
        return new UpdateProfileResponse(
                updatedUser.getId(),
                updatedUser.getFullName(),
                updatedUser.getEmail());
    }
}

