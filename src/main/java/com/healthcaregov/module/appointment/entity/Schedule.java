package com.healthcaregov.module.appointment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity @Table(name = "schedules")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long scheduleId;
    @Column(nullable = false) private Long doctorId;
    @Column(nullable = false) private LocalDate availableDate;
    @Column(nullable = false) private LocalTime timeSlot;
    @Enumerated(EnumType.STRING) @Builder.Default private SlotStatus status = SlotStatus.AVAILABLE;

    public enum SlotStatus { AVAILABLE, BOOKED, BLOCKED }
}
