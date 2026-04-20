package com.healthcaregov.module.appointment.controller;

import com.healthcaregov.module.appointment.dto.*;
import com.healthcaregov.module.appointment.entity.Appointment;
import com.healthcaregov.module.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> create(@Valid @RequestBody AppointmentRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Appointment created", appointmentService.createAppointment(req)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Appointments", appointmentService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Found", appointmentService.getById(id)));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(ApiResponse.success("Patient appointments", appointmentService.getByPatient(patientId)));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(ApiResponse.success("Doctor appointments", appointmentService.getByDoctor(doctorId)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateStatus(
            @PathVariable Long id, @RequestParam Appointment.AppointmentStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", appointmentService.updateStatus(id, status)));
    }

    @PostMapping("/schedules")
    public ResponseEntity<ApiResponse<ScheduleResponse>> createSchedule(@Valid @RequestBody ScheduleRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Schedule created", appointmentService.createSchedule(req)));
    }

    @GetMapping("/schedules/available")
    public ResponseEntity<ApiResponse<List<ScheduleResponse>>> getAvailableSlots(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ApiResponse.success("Available slots", appointmentService.getAvailableSlots(doctorId, date)));
    }
}
