package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// Dữ liệu trả về khi đăng nhập thành công
@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    // JWT token để client dùng cho các request tiếp theo
    private String accessToken;

    // Thời gian hết hạn của token (dạng ISO 8601, ví dụ: "2025-07-31T23:59:59Z")
    private String expire;
}
