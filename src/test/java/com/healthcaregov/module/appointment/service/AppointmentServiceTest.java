package com.healthcaregov.module.appointment.service;

import com.healthcaregov.exception.SlotUnavailableException;
import com.healthcaregov.module.appointment.dto.*;
import com.healthcaregov.module.appointment.entity.*;
import com.healthcaregov.module.appointment.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AppointmentService Tests")
class AppointmentServiceTest {

    @Mock private AppointmentRepository appointmentRepository;
    @Mock private ScheduleRepository scheduleRepository;
    @InjectMocks private AppointmentService appointmentService;

    private Appointment mockAppointment;
    private AppointmentRequest appointmentRequest;

    @BeforeEach
    void setUp() {
        mockAppointment = Appointment.builder().appointmentId(1L).patientId(1L).doctorId(2L)
                .date(LocalDate.of(2026, 4, 20)).time(LocalTime.of(10, 0))
                .status(Appointment.AppointmentStatus.PENDING).build();
        appointmentRequest = new AppointmentRequest();
        appointmentRequest.setPatientId(1L);
        appointmentRequest.setDoctorId(2L);
        appointmentRequest.setDate(LocalDate.of(2026, 4, 20));
        appointmentRequest.setTime(LocalTime.of(10, 0));
    }

    @Test @DisplayName("Should book appointment")
    void shouldBookAppointment() {
        when(appointmentRepository.existsByDoctorIdAndDateAndTimeAndStatusNot(any(), any(), any(), any())).thenReturn(false);
        when(appointmentRepository.save(any())).thenReturn(mockAppointment);
        AppointmentResponse result = appointmentService.book(appointmentRequest);
        assertNotNull(result);
        assertEquals(Appointment.AppointmentStatus.PENDING, result.getStatus());
    }

    @Test @DisplayName("Should throw when slot taken")
    void shouldThrowWhenSlotTaken() {
        when(appointmentRepository.existsByDoctorIdAndDateAndTimeAndStatusNot(any(), any(), any(), any())).thenReturn(true);
        assertThrows(SlotUnavailableException.class, () -> appointmentService.book(appointmentRequest));
    }

    @Test @DisplayName("Should update appointment status")
    void shouldUpdateStatus() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(mockAppointment));
        when(appointmentRepository.save(any())).thenReturn(mockAppointment);
        AppointmentResponse result = appointmentService.updateStatus(1L, Appointment.AppointmentStatus.CONFIRMED);
        assertNotNull(result);
        verify(appointmentRepository, times(1)).save(any());
    }
}
