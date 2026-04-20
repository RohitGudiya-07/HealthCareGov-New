package com.healthcaregov.module.appointment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity @Table(name = "appointments")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long appointmentId;
    @Column(nullable = false) private Long patientId;
    @Column(nullable = false) private Long doctorId;
    @Column(nullable = false) private LocalDate date;
    @Column(nullable = false) private LocalTime time;
    @Enumerated(EnumType.STRING) @Builder.Default private AppointmentStatus status = AppointmentStatus.PENDING;
    private String notes;
    @Column(updatable = false) private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public enum AppointmentStatus { PENDING, CONFIRMED, CANCELLED, COMPLETED, NO_SHOW }
}
