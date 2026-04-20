package com.healthcaregov.module.hospital.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "hospitals")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Hospital {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long hospitalId;
    @Column(nullable = false) private String name;
    @Column(nullable = false, length = 500) private String location;
    @Column(nullable = false) private Integer capacity;
    @Enumerated(EnumType.STRING) @Builder.Default private HospitalStatus status = HospitalStatus.ACTIVE;
    private String contactNumber;
    private String email;
    @Column(updatable = false) private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public enum HospitalStatus { ACTIVE, INACTIVE, UNDER_MAINTENANCE }
}
