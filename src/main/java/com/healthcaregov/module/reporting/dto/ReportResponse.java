package com.healthcaregov.module.reporting.dto;

import com.healthcaregov.module.reporting.entity.Report;
import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReportResponse {
    private Long reportId;
    private Report.ReportScope scope;
    private String metrics;
    private LocalDateTime generatedDate;
    private Long generatedBy;
    private Report.ReportFormat format;
    private String title;
}
