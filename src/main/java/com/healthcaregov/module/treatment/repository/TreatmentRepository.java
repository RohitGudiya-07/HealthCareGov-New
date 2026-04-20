package com.healthcaregov.module.treatment.repository;

import com.healthcaregov.module.treatment.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    List<Treatment> findByPatientIdOrderByDateDesc(Long patientId);
    List<Treatment> findByDoctorId(Long doctorId);
}
