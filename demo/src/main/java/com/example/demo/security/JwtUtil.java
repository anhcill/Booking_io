package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

// Class này chuyên tạo và kiểm tra JWT Token
// Dùng JJWT 0.12.x (API mới)
@Component
public class JwtUtil {

    // Đọc secret key và thời gian hết hạn từ application.properties
    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    // Lấy key để ký token (phải đủ 32 ký tự trở lên cho HS256)
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Tạo JWT token từ email và role của user
    public String generateToken(String email, String role) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(email)          // Lưu email vào token (field "sub")
                .claim("role", role)     // Lưu role vào token (field "role")
                .issuedAt(now)           // Thời gian tạo token
                .expiration(expireDate)  // Thời gian hết hạn
                .signWith(getSigningKey())
                .compact();
    }

    // Lấy thời gian hết hạn dạng ISO 8601 (ví dụ: "2025-07-31T23:59:59Z")
    public String getExpireTime() {
        Instant expire = Instant.now().plusMillis(expirationMs);
        return expire.toString();
    }

    // Lấy email từ token
    public String getEmailFromToken(String token) {
        // JJWT 0.12.x dùng Jwts.parser() thay vì Jwts.parserBuilder()
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Kiểm tra token còn hợp lệ không
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // Token sai hoặc hết hạn
            return false;
        }
    }
}
