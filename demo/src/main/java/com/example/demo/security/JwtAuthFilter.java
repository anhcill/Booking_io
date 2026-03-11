package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

// Filter này chạy trước mỗi request
// Nhiệm vụ: đọc JWT token từ header Authorization, kiểm tra hợp lệ,
// rồi set thông tin user vào SecurityContext để Spring Security nhận ra
// Không dùng @Component để tránh Spring Boot tự đăng ký 2 lần
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // Constructor injection — JwtUtil được truyền vào từ SecurityConfig
    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Bước 1: Đọc header Authorization từ request
        String authHeader = request.getHeader("Authorization");
        System.out.println(">>> [JwtFilter] Authorization header: " + authHeader);

        // Bước 2: Kiểm tra header có tồn tại và bắt đầu bằng "Bearer " không
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println(">>> [JwtFilter] Không có token hoặc sai format → bỏ qua");
            filterChain.doFilter(request, response);
            return;
        }

        // Bước 3: Cắt bỏ chữ "Bearer " để lấy token thực sự
        String token = authHeader.substring(7);
        System.out.println(">>> [JwtFilter] Token: " + token.substring(0, Math.min(20, token.length())) + "...");

        // Bước 4: Kiểm tra token có hợp lệ không
        boolean isValid = jwtUtil.validateToken(token);
        System.out.println(">>> [JwtFilter] Token valid: " + isValid);

        if (!isValid) {
            filterChain.doFilter(request, response);
            return;
        }

        // Bước 5: Lấy email từ token
        String email = jwtUtil.getEmailFromToken(token);
        System.out.println(">>> [JwtFilter] Email từ token: " + email);

        // Bước 6: Set authentication vào SecurityContext
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(">>> [JwtFilter] Đã set authentication thành công!");

        // Bước 7: Cho request tiếp tục
        filterChain.doFilter(request, response);
    }
}
