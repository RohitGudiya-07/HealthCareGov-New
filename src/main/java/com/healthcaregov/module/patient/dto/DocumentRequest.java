package com.healthcaregov.module.patient.dto;

import com.healthcaregov.module.patient.entity.PatientDocument;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DocumentRequest {
    @NotNull private Long patientId;
    @NotNull private PatientDocument.DocType docType;
    @NotBlank private String fileUri;
}
