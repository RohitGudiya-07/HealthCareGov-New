package com.healthcaregov.module.notification.service;

import com.healthcaregov.exception.ResourceNotFoundException;
import com.healthcaregov.module.notification.dto.*;
import com.healthcaregov.module.notification.entity.Notification;
import com.healthcaregov.module.notification.repository.NotificationRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("NotificationService Tests")
class NotificationServiceTest {

    @Mock private NotificationRepository notificationRepository;
    @InjectMocks private NotificationService notificationService;

    private Notification mockNotification;
    private NotificationRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockNotification = Notification.builder()
                .notificationId(1L).userId(1L).entityId(100L)
                .message("Appointment confirmed").category(Notification.NotificationCategory.APPOINTMENT)
                .status(Notification.NotificationStatus.UNREAD)
                .channel(Notification.NotificationChannel.IN_APP)
                .createdDate(LocalDateTime.now()).build();

        mockRequest = new NotificationRequest();
        mockRequest.setUserId(1L);
        mockRequest.setEntityId(100L);
        mockRequest.setMessage("Appointment confirmed");
        mockRequest.setCategory(Notification.NotificationCategory.APPOINTMENT);
        mockRequest.setChannel(Notification.NotificationChannel.IN_APP);
    }

    @Test @DisplayName("Create notification successfully")
    void shouldCreateNotification() {
        when(notificationRepository.save(any())).thenReturn(mockNotification);
        NotificationResponse result = notificationService.createNotification(mockRequest);
        assertNotNull(result);
        assertEquals("Appointment confirmed", result.getMessage());
    }

    @Test @DisplayName("Get unread count for user")
    void shouldGetUnreadCount() {
        when(notificationRepository.countByUserIdAndStatus(1L, Notification.NotificationStatus.UNREAD))
                .thenReturn(3L);
        assertEquals(3L, notificationService.getUnreadCount(1L));
    }

    @Test @DisplayName("Mark notification as read")
    void shouldMarkAsRead() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(mockNotification));
        when(notificationRepository.save(any())).thenReturn(mockNotification);
        NotificationResponse result = notificationService.markAsRead(1L);
        assertNotNull(result);
        verify(notificationRepository).save(any());
    }

    @Test @DisplayName("Throw error when notification not found")
    void shouldThrowWhenNotFound() {
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> notificationService.markAsRead(99L));
    }
}
