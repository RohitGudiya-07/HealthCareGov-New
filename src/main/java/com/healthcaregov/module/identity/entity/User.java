package com.healthcaregov.module.identity.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false) private String name;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private Role role;
    @Column(nullable = false, unique = true) private String email;
    private String phone;
    @Column(nullable = false) private String password;
    @Enumerated(EnumType.STRING) @Builder.Default private UserStatus status = UserStatus.ACTIVE;
    @Column(updatable = false) private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public enum Role { PATIENT, DOCTOR, HOSPITAL_ADMIN, PROGRAM_MANAGER, COMPLIANCE_OFFICER, GOVERNMENT_AUDITOR }
    public enum UserStatus { ACTIVE, INACTIVE, SUSPENDED }
}
