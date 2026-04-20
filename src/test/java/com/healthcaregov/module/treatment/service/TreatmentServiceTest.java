package com.healthcaregov.module.treatment.service;

import com.healthcaregov.exception.ResourceNotFoundException;
import com.healthcaregov.module.treatment.dto.*;
import com.healthcaregov.module.treatment.entity.*;
import com.healthcaregov.module.treatment.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TreatmentService Tests")
class TreatmentServiceTest {

    @Mock private TreatmentRepository treatmentRepository;
    @Mock private MedicalRecordRepository medicalRecordRepository;
    @InjectMocks private TreatmentService treatmentService;

    private Treatment mockTreatment;

    @BeforeEach
    void setUp() {
        mockTreatment = Treatment.builder().treatmentId(1L).patientId(1L).doctorId(2L)
                .diagnosis("Fever").prescription("Paracetamol").date(LocalDate.now())
                .status(Treatment.TreatmentStatus.ONGOING).build();
    }

    @Test @DisplayName("Should create treatment")
    void shouldCreateTreatment() {
        TreatmentRequest req = new TreatmentRequest();
        req.setPatientId(1L); req.setDoctorId(2L);
        req.setDiagnosis("Fever"); req.setDate(LocalDate.now());
        when(treatmentRepository.save(any())).thenReturn(mockTreatment);
        TreatmentResponse result = treatmentService.createTreatment(req);
        assertNotNull(result);
        assertEquals("Fever", result.getDiagnosis());
    }

    @Test @DisplayName("Should get by id")
    void shouldGetById() {
        when(treatmentRepository.findById(1L)).thenReturn(Optional.of(mockTreatment));
        assertNotNull(treatmentService.getById(1L));
    }

    @Test @DisplayName("Should throw when not found")
    void shouldThrowWhenNotFound() {
        when(treatmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> treatmentService.getById(99L));
    }

    @Test @DisplayName("Should update status")
    void shouldUpdateStatus() {
        when(treatmentRepository.findById(1L)).thenReturn(Optional.of(mockTreatment));
        when(treatmentRepository.save(any())).thenReturn(mockTreatment);
        assertNotNull(treatmentService.updateStatus(1L, Treatment.TreatmentStatus.COMPLETED));
    }
}
