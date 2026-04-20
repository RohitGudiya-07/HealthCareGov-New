package com.healthcaregov.module.hospital.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class HospitalRequest {
    @NotBlank private String name;
    @NotBlank private String location;
    @NotNull @Min(1) private Integer capacity;
    private String contactNumber;
    private String email;
}
