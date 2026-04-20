package com.healthcaregov.module.treatment.dto;

import com.healthcaregov.module.treatment.entity.MedicalRecord;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class MedicalRecordResponse {
    private Long recordId;
    private Long patientId;
    private String detailsJson;
    private LocalDate date;
    private MedicalRecord.RecordStatus status;
    private LocalDateTime createdAt;
}
