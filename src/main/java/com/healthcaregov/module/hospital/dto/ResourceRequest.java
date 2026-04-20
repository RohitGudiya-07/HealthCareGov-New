package com.healthcaregov.module.hospital.dto;

import com.healthcaregov.module.hospital.entity.Resource;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ResourceRequest {
    @NotNull private Long hospitalId;
    @NotNull private Resource.ResourceType type;
    @NotNull @Min(1) private Integer quantity;
    @NotNull @Min(0) private Integer availableQuantity;
    private String description;
}
