package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// Dữ liệu trả về khi đăng ký thành công
// Theo API Spec: { "userId": 1, "fullName": "Jane Doe", "email": "jane@example.com" }
@Getter
@Setter
@AllArgsConstructor
public class RegisterResponse {

    private Long userId; // ID của user vừa được tạo
    private String fullName;
    private String email;
}
