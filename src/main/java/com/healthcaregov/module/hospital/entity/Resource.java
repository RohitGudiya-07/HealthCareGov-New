package com.healthcaregov.module.hospital.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "resources")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Resource {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long resourceId;
    @Column(nullable = false) private Long hospitalId;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private ResourceType type;
    @Column(nullable = false) private Integer quantity;
    @Column(nullable = false) private Integer availableQuantity;
    @Enumerated(EnumType.STRING) @Builder.Default private ResourceStatus status = ResourceStatus.AVAILABLE;
    @Column(length = 500) private String description;
    @Column(updatable = false) private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public enum ResourceType { BED, EQUIPMENT, STAFF, MEDICINE, VEHICLE }
    public enum ResourceStatus { AVAILABLE, OCCUPIED, MAINTENANCE, DECOMMISSIONED }
}
