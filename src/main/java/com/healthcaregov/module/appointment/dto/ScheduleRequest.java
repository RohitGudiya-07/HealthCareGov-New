package com.healthcaregov.module.appointment.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleRequest {
    @NotNull private Long doctorId;
    @NotNull private LocalDate availableDate;
    @NotNull private LocalTime timeSlot;
}
