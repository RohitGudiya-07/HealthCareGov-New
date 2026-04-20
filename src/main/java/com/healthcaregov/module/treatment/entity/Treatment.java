package com.healthcaregov.module.treatment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Table(name = "treatments")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Treatment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long treatmentId;
    @Column(nullable = false) private Long patientId;
    @Column(nullable = false) private Long doctorId;
    @Column(nullable = false, length = 1000) private String diagnosis;
    @Column(length = 1000) private String prescription;
    @Column(nullable = false) private LocalDate date;
    @Enumerated(EnumType.STRING) @Builder.Default private TreatmentStatus status = TreatmentStatus.ONGOING;
    @Column(columnDefinition = "TEXT") private String followUpNotes;
    @Column(updatable = false) private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public enum TreatmentStatus { ONGOING, COMPLETED, REFERRED, CANCELLED }
}
