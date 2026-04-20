package com.healthcaregov.module.patient.dto;

import com.healthcaregov.module.patient.entity.PatientDocument;
import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DocumentResponse {
    private Long documentId;
    private Long patientId;
    private PatientDocument.DocType docType;
    private String fileUri;
    private LocalDateTime uploadedDate;
    private PatientDocument.VerificationStatus verificationStatus;
}
