package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// Mỗi lỗi validation sẽ có dạng: { "field": "email", "message": "Invalid email format" }
@Getter
@Setter
@AllArgsConstructor
public class ValidationError {

    private String field; // Tên trường bị lỗi
    private String message; // Thông báo lỗi
}
