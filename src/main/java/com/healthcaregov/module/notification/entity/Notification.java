package com.healthcaregov.module.notification.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "notifications")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long notificationId;
    @Column(nullable = false) private Long userId;
    private Long entityId;
    @Column(nullable = false, length = 500) private String message;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private NotificationCategory category;
    @Enumerated(EnumType.STRING) @Builder.Default private NotificationStatus status = NotificationStatus.UNREAD;
    @Enumerated(EnumType.STRING) @Builder.Default private NotificationChannel channel = NotificationChannel.IN_APP;
    @Column(updatable = false) private LocalDateTime createdDate;
    private LocalDateTime readAt;

    @PrePersist protected void onCreate() { createdDate = LocalDateTime.now(); }

    public enum NotificationCategory { APPOINTMENT, TREATMENT, HOSPITAL, COMPLIANCE, SYSTEM }
    public enum NotificationStatus { UNREAD, READ, ARCHIVED }
    public enum NotificationChannel { IN_APP, EMAIL, SMS }
}
