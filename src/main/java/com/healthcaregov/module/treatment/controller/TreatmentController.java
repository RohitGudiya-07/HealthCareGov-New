package com.healthcaregov.module.treatment.controller;

import com.healthcaregov.module.treatment.dto.*;
import com.healthcaregov.module.treatment.entity.Treatment;
import com.healthcaregov.module.treatment.service.TreatmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/treatments")
@RequiredArgsConstructor
public class TreatmentController {

    private final TreatmentService treatmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<TreatmentResponse>> create(@Valid @RequestBody TreatmentRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Treatment created", treatmentService.createTreatment(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TreatmentResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Found", treatmentService.getById(id)));
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<ApiResponse<List<TreatmentResponse>>> getByPatient(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Treatments", treatmentService.getByPatient(id)));
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<ApiResponse<List<TreatmentResponse>>> getByDoctor(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Treatments", treatmentService.getByDoctor(id)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<TreatmentResponse>> updateStatus(@PathVariable Long id,
                                                                         @RequestParam Treatment.TreatmentStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", treatmentService.updateStatus(id, status)));
    }

    @PostMapping("/medical-records")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> createRecord(@Valid @RequestBody MedicalRecordRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Record created", treatmentService.createMedicalRecord(req)));
    }

    @GetMapping("/medical-records/patient/{id}")
    public ResponseEntity<ApiResponse<List<MedicalRecordResponse>>> getHistory(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("History", treatmentService.getMedicalHistory(id)));
    }
}
