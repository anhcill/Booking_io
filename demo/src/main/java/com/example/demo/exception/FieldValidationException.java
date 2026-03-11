package com.example.demo.exception;

// Exception này dùng khi có lỗi validation trong Service (ví dụ: email đã tồn tại)
// Mang theo thông tin field bị lỗi và message lỗi
// GlobalExceptionHandler sẽ bắt và trả về 422 đúng format
public class FieldValidationException extends RuntimeException {

    private final String field;   // Tên field bị lỗi, ví dụ: "email"
    private final String message; // Thông báo lỗi, ví dụ: "Email is already registered"

    public FieldValidationException(String field, String message) {
        super(message);
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
