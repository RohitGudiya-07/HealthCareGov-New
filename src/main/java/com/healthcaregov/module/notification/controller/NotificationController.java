package com.healthcaregov.module.notification.controller;

import com.healthcaregov.module.notification.dto.*;
import com.healthcaregov.module.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponse>> create(@Valid @RequestBody NotificationRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Notification created", notificationService.createNotification(req)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success("Notifications fetched", notificationService.getByUser(userId)));
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getUnread(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success("Unread notifications", notificationService.getUnreadByUser(userId)));
    }

    @GetMapping("/user/{userId}/unread-count")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success("Unread count", notificationService.getUnreadCount(userId)));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<NotificationResponse>> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Marked as read", notificationService.markAsRead(id)));
    }

    @PatchMapping("/user/{userId}/read-all")
    public ResponseEntity<ApiResponse<Void>> markAllRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok(ApiResponse.success("All marked as read", null));
    }
}
