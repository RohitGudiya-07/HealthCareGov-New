package com.healthcaregov.module.hospital.dto;

import com.healthcaregov.module.hospital.entity.Resource;
import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ResourceResponse {
    private Long resourceId;
    private Long hospitalId;
    private Resource.ResourceType type;
    private Integer quantity;
    private Integer availableQuantity;
    private Resource.ResourceStatus status;
    private String description;
    private LocalDateTime createdAt;
}
