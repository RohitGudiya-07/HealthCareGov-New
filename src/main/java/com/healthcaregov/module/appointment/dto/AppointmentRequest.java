package com.healthcaregov.module.appointment.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRequest {
    @NotNull private Long patientId;
    @NotNull private Long doctorId;
    @NotNull private LocalDate date;
    @NotNull private LocalTime time;
    private String notes;
}
