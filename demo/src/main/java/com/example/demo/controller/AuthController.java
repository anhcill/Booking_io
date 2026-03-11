package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.RegisterResponse;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controller chỉ có nhiệm vụ:
// 1. Nhận request từ client
// 2. Gọi Service xử lý
// 3. Trả response về client
// Toàn bộ logic và xử lý lỗi nằm ở Service và GlobalExceptionHandler
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ============================================================
    // POST /api/auth/login — Đăng nhập
    // ============================================================
    // @Valid tự kiểm tra các rule trong LoginRequest
    // Nếu validation lỗi → GlobalExceptionHandler trả về 422 tự động
    // Nếu sai email/mật khẩu → Service throw UnauthorizedException → GlobalExceptionHandler trả 401
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        // Gọi service xử lý đăng nhập
        LoginResponse loginData = authService.login(request);

        // Thành công → trả về 200 OK
        ApiResponse<LoginResponse> response = new ApiResponse<>(
                true,
                "Login successful",
                loginData);
        return ResponseEntity.ok(response);
    }

    // ============================================================
    // POST /api/auth/register — Đăng ký tài khoản mới
    // ============================================================
    // @Valid tự kiểm tra các rule trong RegisterRequest
    // Nếu validation lỗi → GlobalExceptionHandler trả về 422 tự động
    // Nếu email trùng → Service throw FieldValidationException → GlobalExceptionHandler trả 422
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        // Gọi service xử lý đăng ký
        RegisterResponse registerData = authService.register(request);

        // Thành công → trả về 201 Created
        ApiResponse<RegisterResponse> response = new ApiResponse<>(
                true,
                "Registration successful",
                registerData);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

