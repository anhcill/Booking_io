package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.UpdateProfileRequest;
import com.example.demo.dto.UpdateProfileResponse;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

// Controller xử lý các request liên quan đến thông tin user
// Các API ở đây đều cần JWT token (protected)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AuthService authService;

    // ============================================================
    // PUT /api/user/profile — Cập nhật thông tin cá nhân
    // ============================================================
    // @AuthenticationPrincipal lấy email từ SecurityContext
    // (email được JwtAuthFilter set vào SecurityContext sau khi verify token)
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            @AuthenticationPrincipal String email) {

        // Gọi service xử lý — email lấy từ token (không phải do client gửi)
        UpdateProfileResponse profileData = authService.updateProfile(email, request);

        // Thành công → trả về 200 OK
        ApiResponse<UpdateProfileResponse> response = new ApiResponse<>(
                true,
                "Profile updated successfully",
                profileData);
        return ResponseEntity.ok(response);
    }
}
