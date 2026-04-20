package com.healthcaregov.module.appointment.dto;

import com.healthcaregov.module.appointment.entity.Appointment;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AppointmentResponse {
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private LocalDate date;
    private LocalTime time;
    private Appointment.AppointmentStatus status;
    private String notes;
    private LocalDateTime createdAt;
}
