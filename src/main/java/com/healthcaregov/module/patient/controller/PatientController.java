package com.healthcaregov.module.patient.controller;

import com.healthcaregov.module.patient.dto.*;
import com.healthcaregov.module.patient.entity.Patient;
import com.healthcaregov.module.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponse>> create(@Valid @RequestBody PatientRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Patient created", patientService.createPatient(req)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PatientResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Patients", patientService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Found", patientService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> update(@PathVariable Long id,
                                                                @Valid @RequestBody PatientRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Updated", patientService.update(id, req)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PatientResponse>> updateStatus(@PathVariable Long id,
                                                                      @RequestParam Patient.PatientStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", patientService.updateStatus(id, status)));
    }

    @PostMapping("/documents")
    public ResponseEntity<ApiResponse<DocumentResponse>> uploadDocument(@Valid @RequestBody DocumentRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Document uploaded", patientService.uploadDocument(req)));
    }

    @GetMapping("/{id}/documents")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getDocuments(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Documents", patientService.getDocuments(id)));
    }
}
