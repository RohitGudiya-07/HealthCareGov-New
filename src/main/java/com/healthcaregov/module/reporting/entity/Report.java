package com.healthcaregov.module.reporting.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "reports")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Report {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long reportId;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private ReportScope scope;
    @Column(columnDefinition = "TEXT", nullable = false) private String metrics;
    @Column(nullable = false) private LocalDateTime generatedDate;
    @Column(nullable = false) private Long generatedBy;
    @Enumerated(EnumType.STRING) @Builder.Default private ReportFormat format = ReportFormat.JSON;
    private String title;

    @PrePersist protected void onCreate() { if (generatedDate == null) generatedDate = LocalDateTime.now(); }

    public enum ReportScope { APPOINTMENT, TREATMENT, HOSPITAL, COMPLIANCE, OVERALL }
    public enum ReportFormat { JSON, CSV, PDF }
}
