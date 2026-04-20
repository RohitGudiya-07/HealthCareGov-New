package com.healthcaregov.module.compliance.service;

import com.healthcaregov.exception.ResourceNotFoundException;
import com.healthcaregov.module.compliance.dto.*;
import com.healthcaregov.module.compliance.entity.*;
import com.healthcaregov.module.compliance.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComplianceService {

    private final ComplianceRecordRepository complianceRecordRepository;
    private final AuditRepository auditRepository;

    @Transactional
    public ComplianceRecordResponse createRecord(ComplianceRecordRequest req) {
        ComplianceRecord r = ComplianceRecord.builder().entityId(req.getEntityId()).type(req.getType())
                .result(req.getResult()).date(req.getDate()).notes(req.getNotes()).build();
        r = complianceRecordRepository.save(r);
        log.info("Compliance record created: {}", r.getComplianceId());
        return mapRecord(r);
    }

    public ComplianceRecordResponse getRecordById(Long id) {
        return complianceRecordRepository.findById(id).map(this::mapRecord)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found: " + id));
    }

    public List<ComplianceRecordResponse> getAllRecords() {
        return complianceRecordRepository.findAll().stream().map(this::mapRecord).collect(Collectors.toList());
    }

    public List<ComplianceRecordResponse> getByEntity(Long entityId) {
        return complianceRecordRepository.findByEntityId(entityId).stream().map(this::mapRecord).collect(Collectors.toList());
    }

    public List<ComplianceRecordResponse> getNonCompliant() {
        return complianceRecordRepository.findByResult(ComplianceRecord.ComplianceResult.NON_COMPLIANT)
                .stream().map(this::mapRecord).collect(Collectors.toList());
    }

    @Transactional
    public AuditResponse createAudit(AuditRequest req) {
        Audit a = Audit.builder().officerId(req.getOfficerId()).scope(req.getScope())
                .findings(req.getFindings()).date(req.getDate()).recommendations(req.getRecommendations()).build();
        a = auditRepository.save(a);
        log.info("Audit created: {}", a.getAuditId());
        return mapAudit(a);
    }

    public AuditResponse getAuditById(Long id) {
        return auditRepository.findById(id).map(this::mapAudit)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found: " + id));
    }

    public List<AuditResponse> getAllAudits() {
        return auditRepository.findAll().stream().map(this::mapAudit).collect(Collectors.toList());
    }

    public List<AuditResponse> getAuditsByOfficer(Long officerId) {
        return auditRepository.findByOfficerId(officerId).stream().map(this::mapAudit).collect(Collectors.toList());
    }

    @Transactional
    public AuditResponse updateAuditStatus(Long id, Audit.AuditStatus status) {
        Audit a = auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found: " + id));
        a.setStatus(status);
        return mapAudit(auditRepository.save(a));
    }

    private ComplianceRecordResponse mapRecord(ComplianceRecord r) {
        return ComplianceRecordResponse.builder().complianceId(r.getComplianceId()).entityId(r.getEntityId())
                .type(r.getType()).result(r.getResult()).date(r.getDate()).notes(r.getNotes()).createdAt(r.getCreatedAt()).build();
    }

    private AuditResponse mapAudit(Audit a) {
        return AuditResponse.builder().auditId(a.getAuditId()).officerId(a.getOfficerId()).scope(a.getScope())
                .findings(a.getFindings()).date(a.getDate()).status(a.getStatus())
                .recommendations(a.getRecommendations()).createdAt(a.getCreatedAt()).build();
    }
}
