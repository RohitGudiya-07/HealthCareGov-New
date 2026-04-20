package com.healthcaregov.module.reporting.controller;

import com.healthcaregov.module.reporting.dto.*;
import com.healthcaregov.module.reporting.entity.Report;
import com.healthcaregov.module.reporting.service.ReportingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingService reportingService;

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<ReportResponse>> generate(@Valid @RequestBody ReportRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Report generated", reportingService.generateReport(req)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReportResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Reports", reportingService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReportResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Found", reportingService.getById(id)));
    }

    @GetMapping("/scope/{scope}")
    public ResponseEntity<ApiResponse<List<ReportResponse>>> getByScope(@PathVariable Report.ReportScope scope) {
        return ResponseEntity.ok(ApiResponse.success("Reports", reportingService.getByScope(scope)));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardMetrics>> getDashboard() {
        return ResponseEntity.ok(ApiResponse.success("Dashboard", reportingService.getDashboard()));
    }
}
