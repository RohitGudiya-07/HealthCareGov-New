package com.healthcaregov.module.compliance.service;

import com.healthcaregov.module.compliance.dto.*;
import com.healthcaregov.module.compliance.entity.*;
import com.healthcaregov.module.compliance.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ComplianceService Tests")
class ComplianceServiceTest {

    @Mock private ComplianceRecordRepository complianceRecordRepository;
    @Mock private AuditRepository auditRepository;
    @InjectMocks private ComplianceService complianceService;

    private ComplianceRecord mockRecord;

    @BeforeEach
    void setUp() {
        mockRecord = ComplianceRecord.builder().complianceId(1L).entityId(1L)
                .type(ComplianceRecord.EntityType.HOSPITAL)
                .result(ComplianceRecord.ComplianceResult.COMPLIANT).date(LocalDate.now()).build();
    }

    @Test @DisplayName("Should create compliance record")
    void shouldCreateRecord() {
        ComplianceRecordRequest req = new ComplianceRecordRequest();
        req.setEntityId(1L); req.setType(ComplianceRecord.EntityType.HOSPITAL);
        req.setResult(ComplianceRecord.ComplianceResult.COMPLIANT); req.setDate(LocalDate.now());
        when(complianceRecordRepository.save(any())).thenReturn(mockRecord);
        ComplianceRecordResponse result = complianceService.createRecord(req);
        assertNotNull(result);
        assertEquals(ComplianceRecord.ComplianceResult.COMPLIANT, result.getResult());
    }

    @Test @DisplayName("Should get non-compliant records")
    void shouldGetNonCompliant() {
        when(complianceRecordRepository.findByResult(ComplianceRecord.ComplianceResult.NON_COMPLIANT)).thenReturn(List.of());
        assertTrue(complianceService.getNonCompliant().isEmpty());
    }

    @Test @DisplayName("Should create audit")
    void shouldCreateAudit() {
        AuditRequest req = new AuditRequest();
        req.setOfficerId(1L); req.setScope("Annual Audit"); req.setDate(LocalDate.now());
        Audit mockAudit = Audit.builder().auditId(1L).officerId(1L).scope("Annual Audit")
                .date(LocalDate.now()).status(Audit.AuditStatus.IN_PROGRESS).build();
        when(auditRepository.save(any())).thenReturn(mockAudit);
        AuditResponse result = complianceService.createAudit(req);
        assertNotNull(result);
        assertEquals("Annual Audit", result.getScope());
    }
}
