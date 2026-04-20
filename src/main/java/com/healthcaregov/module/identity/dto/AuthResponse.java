package com.healthcaregov.module.identity.dto;

import com.healthcaregov.module.identity.entity.User;
import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class AuthResponse {
    private String token;
    private String tokenType;
    private Long userId;
    private String name;
    private String email;
    private User.Role role;
    private long expiresIn;
}
