package com.healthcaregov.module.hospital.controller;

import com.healthcaregov.module.hospital.dto.*;
import com.healthcaregov.module.hospital.entity.Resource;
import com.healthcaregov.module.hospital.service.HospitalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @PostMapping
    public ResponseEntity<ApiResponse<HospitalResponse>> create(@Valid @RequestBody HospitalRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Hospital created", hospitalService.createHospital(req)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HospitalResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Hospitals", hospitalService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HospitalResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Found", hospitalService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HospitalResponse>> update(@PathVariable Long id,
                                                                  @Valid @RequestBody HospitalRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Updated", hospitalService.update(id, req)));
    }

    @PostMapping("/resources")
    public ResponseEntity<ApiResponse<ResourceResponse>> addResource(@Valid @RequestBody ResourceRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Resource added", hospitalService.addResource(req)));
    }

    @GetMapping("/{id}/resources")
    public ResponseEntity<ApiResponse<List<ResourceResponse>>> getResources(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Resources", hospitalService.getResources(id)));
    }

    @PatchMapping("/resources/{id}/status")
    public ResponseEntity<ApiResponse<ResourceResponse>> updateStatus(@PathVariable Long id,
                                                                        @RequestParam Resource.ResourceStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", hospitalService.updateResourceStatus(id, status)));
    }
}
