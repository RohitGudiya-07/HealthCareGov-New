package com.healthcaregov.module.treatment.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TreatmentRequest {
    @NotNull private Long patientId;
    @NotNull private Long doctorId;
    @NotBlank private String diagnosis;
    private String prescription;
    @NotNull private LocalDate date;
    private String followUpNotes;
}
