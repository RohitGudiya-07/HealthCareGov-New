package com.healthcaregov.module.reporting.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DashboardMetrics {
    private long totalReports;
    private long appointmentReports;
    private long treatmentReports;
    private long hospitalReports;
    private long complianceReports;
    private long overallReports;
    private LocalDateTime generatedAt;
}
