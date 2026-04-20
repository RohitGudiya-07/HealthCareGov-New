package com.healthcaregov.module.compliance.dto;

import com.healthcaregov.module.compliance.entity.ComplianceRecord;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ComplianceRecordResponse {
    private Long complianceId;
    private Long entityId;
    private ComplianceRecord.EntityType type;
    private ComplianceRecord.ComplianceResult result;
    private LocalDate date;
    private String notes;
    private LocalDateTime createdAt;
}
