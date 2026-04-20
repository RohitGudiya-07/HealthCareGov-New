package com.healthcaregov.module.compliance.dto;

import com.healthcaregov.module.compliance.entity.Audit;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AuditResponse {
    private Long auditId;
    private Long officerId;
    private String scope;
    private String findings;
    private LocalDate date;
    private Audit.AuditStatus status;
    private String recommendations;
    private LocalDateTime createdAt;
}
