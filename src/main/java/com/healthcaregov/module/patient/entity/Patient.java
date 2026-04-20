package com.healthcaregov.module.patient.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Table(name = "patients")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long patientId;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private LocalDate dob;
    @Enumerated(EnumType.STRING) private Gender gender;
    @Column(length = 500) private String address;
    private String contactInfo;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING) @Builder.Default private PatientStatus status = PatientStatus.ACTIVE;
    @Column(updatable = false) private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public enum Gender { MALE, FEMALE, OTHER }
    public enum PatientStatus { ACTIVE, INACTIVE, DECEASED }
}
