package com.healthcaregov.module.notification.repository;

import com.healthcaregov.module.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    List<Notification> findByUserIdAndStatus(Long userId, Notification.NotificationStatus status);
    List<Notification> findByUserIdOrderByCreatedDateDesc(Long userId);
    long countByUserIdAndStatus(Long userId, Notification.NotificationStatus status);
}
