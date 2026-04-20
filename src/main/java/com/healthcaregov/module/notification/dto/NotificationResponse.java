package com.healthcaregov.module.notification.dto;

import com.healthcaregov.module.notification.entity.Notification;
import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class NotificationResponse {
    private Long notificationId;
    private Long userId;
    private Long entityId;
    private String message;
    private Notification.NotificationCategory category;
    private Notification.NotificationStatus status;
    private Notification.NotificationChannel channel;
    private LocalDateTime createdDate;
    private LocalDateTime readAt;
}
