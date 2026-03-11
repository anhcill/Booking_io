package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.RegisterResponse;
import com.example.demo.dto.UpdateProfileRequest;
import com.example.demo.dto.UpdateProfileResponse;

// Interface định nghĩa các chức năng auth
// Tách interface và implementation để code dễ mở rộng sau này
public interface AuthService {

    // Đăng nhập — trả về token nếu thành công
    LoginResponse login(LoginRequest request);

    // Đăng ký tài khoản mới — trả về thông tin user vừa tạo
    RegisterResponse register(RegisterRequest request);

    // Cập nhật thông tin cá nhân — email lấy từ JWT token (không cho client tự sửa email)
    UpdateProfileResponse updateProfile(String email, UpdateProfileRequest request);
}
