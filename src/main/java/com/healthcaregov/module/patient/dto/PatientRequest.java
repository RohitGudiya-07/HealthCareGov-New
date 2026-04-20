package com.healthcaregov.module.patient.dto;

import com.healthcaregov.module.patient.entity.Patient;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientRequest {
    @NotBlank private String name;
    @NotNull private LocalDate dob;
    private Patient.Gender gender;
    private String address;
    private String contactInfo;
    @Email private String email;
    private String phone;
}
