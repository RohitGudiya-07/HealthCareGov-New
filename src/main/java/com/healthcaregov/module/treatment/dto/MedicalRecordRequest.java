package com.healthcaregov.module.treatment.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MedicalRecordRequest {
    @NotNull private Long patientId;
    @NotBlank private String detailsJson;
    @NotNull private LocalDate date;
}
