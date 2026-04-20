package com.healthcaregov.module.reporting.service;

import com.healthcaregov.exception.ResourceNotFoundException;
import com.healthcaregov.module.reporting.dto.*;
import com.healthcaregov.module.reporting.entity.Report;
import com.healthcaregov.module.reporting.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportingService {

    private final ReportRepository reportRepository;

    @Transactional
    public ReportResponse generateReport(ReportRequest req) {
        String metrics = buildMetrics(req.getScope());
        Report r = Report.builder().scope(req.getScope()).metrics(metrics)
                .generatedBy(req.getGeneratedBy()).format(req.getFormat())
                .title(req.getTitle() != null ? req.getTitle() : req.getScope().name() + " Report").build();
        r = reportRepository.save(r);
        log.info("Report generated: {} scope={}", r.getReportId(), r.getScope());
        return mapReport(r);
    }

    public ReportResponse getById(Long id) {
        return reportRepository.findById(id).map(this::mapReport)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found: " + id));
    }

    public List<ReportResponse> getAll() {
        return reportRepository.findAll().stream().map(this::mapReport).collect(Collectors.toList());
    }

    public List<ReportResponse> getByScope(Report.ReportScope scope) {
        return reportRepository.findByScope(scope).stream().map(this::mapReport).collect(Collectors.toList());
    }

    public DashboardMetrics getDashboard() {
        return DashboardMetrics.builder()
                .totalReports(reportRepository.count())
                .appointmentReports(reportRepository.countByScope(Report.ReportScope.APPOINTMENT))
                .treatmentReports(reportRepository.countByScope(Report.ReportScope.TREATMENT))
                .hospitalReports(reportRepository.countByScope(Report.ReportScope.HOSPITAL))
                .complianceReports(reportRepository.countByScope(Report.ReportScope.COMPLIANCE))
                .overallReports(reportRepository.countByScope(Report.ReportScope.OVERALL))
                .generatedAt(LocalDateTime.now()).build();
    }

    private String buildMetrics(Report.ReportScope scope) {
        return "{\"scope\":\"" + scope.name() + "\",\"generatedAt\":\"" + LocalDateTime.now() + "\",\"status\":\"generated\"}";
    }

    private ReportResponse mapReport(Report r) {
        return ReportResponse.builder().reportId(r.getReportId()).scope(r.getScope()).metrics(r.getMetrics())
                .generatedDate(r.getGeneratedDate()).generatedBy(r.getGeneratedBy())
                .format(r.getFormat()).title(r.getTitle()).build();
    }
}
