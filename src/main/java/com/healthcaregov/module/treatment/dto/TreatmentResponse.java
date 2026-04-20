package com.healthcaregov.module.treatment.dto;

import com.healthcaregov.module.treatment.entity.Treatment;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TreatmentResponse {
    private Long treatmentId;
    private Long patientId;
    private Long doctorId;
    private String diagnosis;
    private String prescription;
    private LocalDate date;
    private Treatment.TreatmentStatus status;
    private String followUpNotes;
    private LocalDateTime createdAt;
}
