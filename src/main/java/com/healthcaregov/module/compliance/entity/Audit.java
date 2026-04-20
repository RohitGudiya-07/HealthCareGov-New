package com.healthcaregov.module.compliance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Table(name = "audits")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Audit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long auditId;
    @Column(nullable = false) private Long officerId;
    @Column(nullable = false) private String scope;
    @Column(columnDefinition = "TEXT") private String findings;
    @Column(nullable = false) private LocalDate date;
    @Enumerated(EnumType.STRING) @Builder.Default private AuditStatus status = AuditStatus.IN_PROGRESS;
    @Column(columnDefinition = "TEXT") private String recommendations;
    @Column(updatable = false) private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public enum AuditStatus { IN_PROGRESS, COMPLETED, ESCALATED, CLOSED }
}
