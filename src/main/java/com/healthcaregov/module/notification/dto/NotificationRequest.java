package com.healthcaregov.module.notification.dto;

import com.healthcaregov.module.notification.entity.Notification;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class NotificationRequest {
    @NotNull private Long userId;
    private Long entityId;
    @NotBlank @Size(max = 500) private String message;
    @NotNull private Notification.NotificationCategory category;
    private Notification.NotificationChannel channel = Notification.NotificationChannel.IN_APP;
}
