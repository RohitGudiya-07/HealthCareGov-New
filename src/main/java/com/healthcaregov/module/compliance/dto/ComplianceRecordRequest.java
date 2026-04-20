package com.healthcaregov.module.compliance.dto;

import com.healthcaregov.module.compliance.entity.ComplianceRecord;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ComplianceRecordRequest {
    @NotNull private Long entityId;
    @NotNull private ComplianceRecord.EntityType type;
    @NotNull private ComplianceRecord.ComplianceResult result;
    @NotNull private LocalDate date;
    private String notes;
}
