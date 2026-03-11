package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// Dữ liệu trả về sau khi cập nhật profile thành công
@Getter
@Setter
@AllArgsConstructor
public class UpdateProfileResponse {

    private Long userId;
    private String fullName;
    private String email;
}
