package com.healthcaregov.module.patient.dto;

import com.healthcaregov.module.patient.entity.Patient;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PatientResponse {
    private Long patientId;
    private String name;
    private LocalDate dob;
    private Patient.Gender gender;
    private String address;
    private String contactInfo;
    private String email;
    private String phone;
    private Patient.PatientStatus status;
    private LocalDateTime createdAt;
}
