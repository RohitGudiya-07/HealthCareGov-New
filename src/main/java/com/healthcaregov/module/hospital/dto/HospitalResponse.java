package com.healthcaregov.module.hospital.dto;

import com.healthcaregov.module.hospital.entity.Hospital;
import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class HospitalResponse {
    private Long hospitalId;
    private String name;
    private String location;
    private Integer capacity;
    private Hospital.HospitalStatus status;
    private String contactNumber;
    private String email;
    private LocalDateTime createdAt;
}
