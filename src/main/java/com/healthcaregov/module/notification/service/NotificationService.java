package com.healthcaregov.module.notification.service;

import com.healthcaregov.exception.ResourceNotFoundException;
import com.healthcaregov.module.notification.dto.*;
import com.healthcaregov.module.notification.entity.Notification;
import com.healthcaregov.module.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public NotificationResponse createNotification(NotificationRequest req) {
        Notification n = Notification.builder()
                .userId(req.getUserId()).entityId(req.getEntityId())
                .message(req.getMessage()).category(req.getCategory())
                .channel(req.getChannel()).build();
        n = notificationRepository.save(n);
        log.info("Notification created for userId={}", req.getUserId());
        return mapNotification(n);
    }

    public List<NotificationResponse> getByUser(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedDateDesc(userId)
                .stream().map(this::mapNotification).collect(Collectors.toList());
    }

    public List<NotificationResponse> getUnreadByUser(Long userId) {
        return notificationRepository.findByUserIdAndStatus(userId, Notification.NotificationStatus.UNREAD)
                .stream().map(this::mapNotification).collect(Collectors.toList());
    }

    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndStatus(userId, Notification.NotificationStatus.UNREAD);
    }

    @Transactional
    public NotificationResponse markAsRead(Long id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found: " + id));
        n.setStatus(Notification.NotificationStatus.READ);
        n.setReadAt(LocalDateTime.now());
        return mapNotification(notificationRepository.save(n));
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> unread = notificationRepository
                .findByUserIdAndStatus(userId, Notification.NotificationStatus.UNREAD);
        unread.forEach(n -> { n.setStatus(Notification.NotificationStatus.READ); n.setReadAt(LocalDateTime.now()); });
        notificationRepository.saveAll(unread);
    }

    private NotificationResponse mapNotification(Notification n) {
        return NotificationResponse.builder()
                .notificationId(n.getNotificationId()).userId(n.getUserId())
                .entityId(n.getEntityId()).message(n.getMessage())
                .category(n.getCategory()).status(n.getStatus())
                .channel(n.getChannel()).createdDate(n.getCreatedDate())
                .readAt(n.getReadAt()).build();
    }
}
