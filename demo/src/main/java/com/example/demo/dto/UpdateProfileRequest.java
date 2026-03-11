package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

// Dữ liệu client gửi lên khi muốn cập nhật thông tin cá nhân
@Getter
@Setter
public class UpdateProfileRequest {

    // Họ tên mới — không được để trống
    @NotBlank(message = "Full name is required")
    private String fullName;
}
