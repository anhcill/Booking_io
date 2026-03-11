package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

// Class dùng chung để wrap mọi response trả về cho client
// Cấu trúc: { "success": true/false, "message": "...", "data": {...} }
@Getter
@Setter
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data; // Dữ liệu trả về (có thể là bất kỳ kiểu gì)

    // Constructor khi thành công và có data
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Constructor khi thất bại (không có data)
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }
}
