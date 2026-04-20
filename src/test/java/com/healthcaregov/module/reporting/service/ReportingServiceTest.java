package com.healthcaregov.module.reporting.service;

import com.healthcaregov.module.reporting.dto.*;
import com.healthcaregov.module.reporting.entity.Report;
import com.healthcaregov.module.reporting.repository.ReportRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReportingService Tests")
class ReportingServiceTest {

    @Mock private ReportRepository reportRepository;
    @InjectMocks private ReportingService reportingService;

    private Report mockReport;

    @BeforeEach
    void setUp() {
        mockReport = Report.builder().reportId(1L).scope(Report.ReportScope.OVERALL)
                .metrics("{"status":"generated"}").generatedDate(LocalDateTime.now())
                .generatedBy(1L).format(Report.ReportFormat.JSON).title("Test Report").build();
    }

    @Test @DisplayName("Should generate report")
    void shouldGenerateReport() {
        ReportRequest req = new ReportRequest();
        req.setScope(Report.ReportScope.OVERALL); req.setGeneratedBy(1L);
        when(reportRepository.save(any())).thenReturn(mockReport);
        ReportResponse result = reportingService.generateReport(req);
        assertNotNull(result);
        assertEquals(Report.ReportScope.OVERALL, result.getScope());
    }

    @Test @DisplayName("Should return dashboard metrics")
    void shouldReturnDashboard() {
        when(reportRepository.count()).thenReturn(10L);
        when(reportRepository.countByScope(any())).thenReturn(2L);
        DashboardMetrics result = reportingService.getDashboard();
        assertNotNull(result);
        assertEquals(10L, result.getTotalReports());
    }

    @Test @DisplayName("Should get all reports")
    void shouldGetAll() {
        when(reportRepository.findAll()).thenReturn(List.of(mockReport));
        assertEquals(1, reportingService.getAll().size());
    }
}
