package com.healthcaregov.module.identity.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "audit_logs")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AuditLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long auditId;
    @Column(nullable = false) private Long userId;
    @Column(nullable = false) private String action;
    @Column(nullable = false) private String resource;
    private LocalDateTime timestamp;
    private String ipAddress;
    private String outcome;

    @PrePersist protected void onCreate() { if (timestamp == null) timestamp = LocalDateTime.now(); }
}
