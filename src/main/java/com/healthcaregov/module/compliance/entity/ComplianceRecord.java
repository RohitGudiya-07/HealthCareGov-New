package com.healthcaregov.module.compliance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Table(name = "compliance_records")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ComplianceRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long complianceId;
    @Column(nullable = false) private Long entityId;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private EntityType type;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private ComplianceResult result;
    @Column(nullable = false) private LocalDate date;
    @Column(length = 1000) private String notes;
    @Column(updatable = false) private LocalDateTime createdAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }

    public enum EntityType { APPOINTMENT, TREATMENT, HOSPITAL, USER }
    public enum ComplianceResult { COMPLIANT, NON_COMPLIANT, PENDING_REVIEW, EXEMPTED }
}
