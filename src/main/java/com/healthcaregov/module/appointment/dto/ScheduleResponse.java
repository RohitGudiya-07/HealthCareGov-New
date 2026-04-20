package com.healthcaregov.module.appointment.dto;

import com.healthcaregov.module.appointment.entity.Schedule;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ScheduleResponse {
    private Long scheduleId;
    private Long doctorId;
    private LocalDate availableDate;
    private LocalTime timeSlot;
    private Schedule.SlotStatus status;
}
