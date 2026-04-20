package com.healthcaregov.module.compliance.controller;

import com.healthcaregov.module.compliance.dto.*;
import com.healthcaregov.module.compliance.entity.Audit;
import com.healthcaregov.module.compliance.service.ComplianceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/compliance")
@RequiredArgsConstructor
public class ComplianceController {

    private final ComplianceService complianceService;

    @PostMapping("/records")
    public ResponseEntity<ApiResponse<ComplianceRecordResponse>> createRecord(@Valid @RequestBody ComplianceRecordRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Record created", complianceService.createRecord(req)));
    }

    @GetMapping("/records")
    public ResponseEntity<ApiResponse<List<ComplianceRecordResponse>>> getAllRecords() {
        return ResponseEntity.ok(ApiResponse.success("Records", complianceService.getAllRecords()));
    }

    @GetMapping("/records/{id}")
    public ResponseEntity<ApiResponse<ComplianceRecordResponse>> getRecord(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Found", complianceService.getRecordById(id)));
    }

    @GetMapping("/records/entity/{id}")
    public ResponseEntity<ApiResponse<List<ComplianceRecordResponse>>> getByEntity(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Records", complianceService.getByEntity(id)));
    }

    @GetMapping("/records/non-compliant")
    public ResponseEntity<ApiResponse<List<ComplianceRecordResponse>>> getNonCompliant() {
        return ResponseEntity.ok(ApiResponse.success("Non-compliant", complianceService.getNonCompliant()));
    }

    @PostMapping("/audits")
    public ResponseEntity<ApiResponse<AuditResponse>> createAudit(@Valid @RequestBody AuditRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Audit created", complianceService.createAudit(req)));
    }

    @GetMapping("/audits")
    public ResponseEntity<ApiResponse<List<AuditResponse>>> getAllAudits() {
        return ResponseEntity.ok(ApiResponse.success("Audits", complianceService.getAllAudits()));
    }

    @GetMapping("/audits/{id}")
    public ResponseEntity<ApiResponse<AuditResponse>> getAudit(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Found", complianceService.getAuditById(id)));
    }

    @GetMapping("/audits/officer/{id}")
    public ResponseEntity<ApiResponse<List<AuditResponse>>> getByOfficer(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Audits", complianceService.getAuditsByOfficer(id)));
    }

    @PatchMapping("/audits/{id}/status")
    public ResponseEntity<ApiResponse<AuditResponse>> updateStatus(@PathVariable Long id,
                                                                     @RequestParam Audit.AuditStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", complianceService.updateAuditStatus(id, status)));
    }
}
