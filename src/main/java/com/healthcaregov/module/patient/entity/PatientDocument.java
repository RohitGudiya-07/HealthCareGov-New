package com.healthcaregov.module.patient.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "patient_documents")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PatientDocument {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long documentId;
    @Column(nullable = false) private Long patientId;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private DocType docType;
    @Column(nullable = false) private String fileUri;
    private LocalDateTime uploadedDate;
    @Enumerated(EnumType.STRING) @Builder.Default private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @PrePersist protected void onCreate() { if (uploadedDate == null) uploadedDate = LocalDateTime.now(); }

    public enum DocType { ID_PROOF, HEALTH_CARD, INSURANCE, OTHER }
    public enum VerificationStatus { PENDING, VERIFIED, REJECTED }
}
