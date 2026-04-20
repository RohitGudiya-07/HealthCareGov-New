package com.healthcaregov.module.hospital.service;

import com.healthcaregov.exception.ResourceNotFoundException;
import com.healthcaregov.module.hospital.dto.*;
import com.healthcaregov.module.hospital.entity.*;
import com.healthcaregov.module.hospital.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("HospitalService Tests")
class HospitalServiceTest {

    @Mock private HospitalRepository hospitalRepository;
    @Mock private ResourceRepository resourceRepository;
    @InjectMocks private HospitalService hospitalService;

    private Hospital mockHospital;

    @BeforeEach
    void setUp() {
        mockHospital = Hospital.builder().hospitalId(1L).name("City Hospital")
                .location("Mumbai").capacity(500).status(Hospital.HospitalStatus.ACTIVE).build();
    }

    @Test @DisplayName("Should create hospital")
    void shouldCreateHospital() {
        HospitalRequest req = new HospitalRequest();
        req.setName("City Hospital"); req.setLocation("Mumbai"); req.setCapacity(500);
        when(hospitalRepository.save(any())).thenReturn(mockHospital);
        HospitalResponse result = hospitalService.createHospital(req);
        assertNotNull(result);
        assertEquals("City Hospital", result.getName());
    }

    @Test @DisplayName("Should get all hospitals")
    void shouldGetAll() {
        when(hospitalRepository.findAll()).thenReturn(List.of(mockHospital));
        assertEquals(1, hospitalService.getAll().size());
    }

    @Test @DisplayName("Should throw when hospital not found")
    void shouldThrow() {
        when(hospitalRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> hospitalService.getById(99L));
    }
}
