package com.example.demo.exception;

import com.example.demo.dto.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Class này bắt tất cả các lỗi xảy ra trong ứng dụng
// Thay vì để mỗi Controller tự xử lý, mình tập trung toàn bộ xử lý lỗi ở đây
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ============================================================
    // Bắt lỗi validation annotation (@NotBlank, @Email, @Pattern...)
    // Trả về HTTP 422
    // ============================================================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException ex) {

        // Lấy danh sách tất cả các lỗi validation
        List<ValidationError> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            // Mỗi lỗi gồm: tên field + thông báo lỗi
            errors.add(new ValidationError(
                    fieldError.getField(),          // ví dụ: "email"
                    fieldError.getDefaultMessage()  // ví dụ: "Invalid email format"
            ));
        });

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("message", "Validation failed");
        body.put("errors", errors);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
    }

    // ============================================================
    // Bắt lỗi validation từ Service (ví dụ: email đã tồn tại)
    // Trả về HTTP 422 — cùng format với validation annotation
    // ============================================================
    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<?> handleFieldValidationError(FieldValidationException ex) {

        List<ValidationError> errors = new ArrayList<>();
        errors.add(new ValidationError(ex.getField(), ex.getMessage()));

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("message", "Validation failed");
        body.put("errors", errors);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
    }

    // ============================================================
    // Bắt lỗi đăng nhập thất bại (sai email hoặc mật khẩu)
    // Trả về HTTP 401
    // ============================================================
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorized(UnauthorizedException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
}

