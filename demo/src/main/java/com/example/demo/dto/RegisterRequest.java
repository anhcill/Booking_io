package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

// Dữ liệu client gửi lên khi đăng ký tài khoản
@Getter
@Setter
public class RegisterRequest {

    // Họ tên không được để trống
    @NotBlank(message = "Full name is required")
    private String fullName;

    // Email không được để trống và phải đúng định dạng
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    // Mật khẩu phải đủ 8 ký tự và chứa cả chữ lẫn số
    // Regex giải thích: phải có ít nhất 1 chữ cái, 1 chữ số, tổng tối thiểu 8 ký tự
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$", message = "Password must be at least 8 characters and contain letters and numbers")
    private String password;
}
