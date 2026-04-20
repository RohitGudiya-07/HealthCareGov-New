package com.healthcaregov.module.patient.service;

import com.healthcaregov.exception.DuplicateResourceException;
import com.healthcaregov.module.patient.dto.*;
import com.healthcaregov.module.patient.entity.*;
import com.healthcaregov.module.patient.repository.*;
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
@DisplayName("PatientService Tests")
class PatientServiceTest {

    @Mock private PatientRepository patientRepository;
    @Mock private PatientDocumentRepository documentRepository;
    @InjectMocks private PatientService patientService;

    private Patient mockPatient;
    private PatientRequest patientRequest;

    @BeforeEach
    void setUp() {
        mockPatient = Patient.builder().patientId(1L).name("Test Patient")
                .dob(LocalDate.of(1995, 6, 15)).gender(Patient.Gender.FEMALE)
                .email("patient@test.com").status(Patient.PatientStatus.ACTIVE).build();
        patientRequest = new PatientRequest();
        patientRequest.setName("Test Patient");
        patientRequest.setDob(LocalDate.of(1995, 6, 15));
        patientRequest.setGender(Patient.Gender.FEMALE);
        patientRequest.setEmail("patient@test.com");
    }

    @Test @DisplayName("Should register patient successfully")
    void shouldRegisterPatient() {
        when(patientRepository.existsByEmail(anyString())).thenReturn(false);
        when(patientRepository.save(any())).thenReturn(mockPatient);
        PatientResponse result = patientService.registerPatient(patientRequest);
        assertNotNull(result);
        assertEquals("Test Patient", result.getName());
        verify(patientRepository, times(1)).save(any());
    }

    @Test @DisplayName("Should throw for duplicate email")
    void shouldThrowForDuplicate() {
        when(patientRepository.existsByEmail(anyString())).thenReturn(true);
        assertThrows(DuplicateResourceException.class, () -> patientService.registerPatient(patientRequest));
    }

    @Test @DisplayName("Should get all patients")
    void shouldGetAllPatients() {
        when(patientRepository.findAll()).thenReturn(List.of(mockPatient));
        List<PatientResponse> result = patientService.getAll();
        assertEquals(1, result.size());
    }

    @Test @DisplayName("Should get patient by id")
    void shouldGetById() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(mockPatient));
        PatientResponse result = patientService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getPatientId());
    }
}
