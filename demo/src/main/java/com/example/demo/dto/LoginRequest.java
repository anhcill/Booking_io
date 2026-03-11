package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

// Đây là class chứa dữ liệu mà client gửi lên khi đăng nhập
@Getter
@Setter
public class LoginRequest {

    // Email không được để trống và phải đúng định dạng
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    // Mật khẩu không được để trống
    @NotBlank(message = "Password is required")
    private String password;
}
