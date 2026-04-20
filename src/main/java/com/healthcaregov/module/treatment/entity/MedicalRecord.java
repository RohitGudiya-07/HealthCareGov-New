package com.healthcaregov.module.treatment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Table(name = "medical_records")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicalRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long recordId;
    @Column(nullable = false) private Long patientId;
    @Column(columnDefinition = "TEXT", nullable = false) private String detailsJson;
    @Column(nullable = false) private LocalDate date;
    @Enumerated(EnumType.STRING) @Builder.Default private RecordStatus status = RecordStatus.ACTIVE;
    @Column(updatable = false) private LocalDateTime createdAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }

    public enum RecordStatus { ACTIVE, ARCHIVED }
}
