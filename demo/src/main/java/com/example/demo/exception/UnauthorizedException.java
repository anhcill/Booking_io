package com.example.demo.exception;

// Exception này dùng khi đăng nhập thất bại (sai email hoặc mật khẩu)
// Service sẽ throw exception này, GlobalExceptionHandler sẽ bắt và trả về 401
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
