package com.healthcaregov.module.reporting.dto;

import com.healthcaregov.module.reporting.entity.Report;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReportRequest {
    @NotNull private Report.ReportScope scope;
    @NotNull private Long generatedBy;
    private String title;
    private Report.ReportFormat format = Report.ReportFormat.JSON;
}
