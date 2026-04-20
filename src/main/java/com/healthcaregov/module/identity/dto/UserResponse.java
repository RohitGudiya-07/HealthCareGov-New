package com.healthcaregov.module.identity.dto;

import com.healthcaregov.module.identity.entity.User;
import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class UserResponse {
    private Long userId;
    private String name;
    private User.Role role;
    private String email;
    private String phone;
    private User.UserStatus status;
    private LocalDateTime createdAt;
}
