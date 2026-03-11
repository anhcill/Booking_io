package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Cấu hình Spring Security cho ứng dụng
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Inject JwtUtil để tự tạo JwtAuthFilter — không dùng @Autowired JwtAuthFilter
    // vì nếu JwtAuthFilter là @Component, Spring Boot sẽ tự đăng ký thêm 1 lần nữa (2 lần tổng cộng) gây lỗi 403
    @Autowired
    private JwtUtil jwtUtil;

    // Cấu hình các rule bảo mật
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Tắt CSRF vì mình dùng JWT (stateless)
                .csrf(AbstractHttpConfigurer::disable)

                // Cấu hình quyền truy cập từng endpoint
                .authorizeHttpRequests(auth -> auth
                        // Cho phép tất cả gọi các API đăng ký/đăng nhập mà không cần token
                        .requestMatchers("/api/auth/**").permitAll()
                        // Các API còn lại phải có token
                        .anyRequest().authenticated())

                // Thêm JwtAuthFilter chạy TRƯỚC filter xác thực mặc định của Spring
                // Filter này sẽ đọc token từ header và set user vào SecurityContext
                .addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Bean dùng để mã hóa mật khẩu bằng BCrypt
    // BCrypt tự động thêm "salt" nên rất an toàn
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

