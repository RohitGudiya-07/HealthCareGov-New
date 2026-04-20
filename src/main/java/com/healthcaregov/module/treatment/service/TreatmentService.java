package com.healthcaregov.module.treatment.service;

import com.healthcaregov.exception.ResourceNotFoundException;
import com.healthcaregov.module.treatment.dto.*;
import com.healthcaregov.module.treatment.entity.*;
import com.healthcaregov.module.treatment.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    @Transactional
    public TreatmentResponse createTreatment(TreatmentRequest req) {
        Treatment t = Treatment.builder().patientId(req.getPatientId()).doctorId(req.getDoctorId())
                .diagnosis(req.getDiagnosis()).prescription(req.getPrescription())
                .date(req.getDate()).followUpNotes(req.getFollowUpNotes()).build();
        t = treatmentRepository.save(t);
        log.info("Treatment created: {}", t.getTreatmentId());
        return mapTreatment(t);
    }

    public TreatmentResponse getById(Long id) {
        return treatmentRepository.findById(id).map(this::mapTreatment)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found: " + id));
    }

    public List<TreatmentResponse> getByPatient(Long pid) {
        return treatmentRepository.findByPatientIdOrderByDateDesc(pid).stream().map(this::mapTreatment).collect(Collectors.toList());
    }

    public List<TreatmentResponse> getByDoctor(Long did) {
        return treatmentRepository.findByDoctorId(did).stream().map(this::mapTreatment).collect(Collectors.toList());
    }

    @Transactional
    public TreatmentResponse updateStatus(Long id, Treatment.TreatmentStatus status) {
        Treatment t = treatmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found: " + id));
        t.setStatus(status);
        return mapTreatment(treatmentRepository.save(t));
    }

    @Transactional
    public MedicalRecordResponse createMedicalRecord(MedicalRecordRequest req) {
        MedicalRecord r = MedicalRecord.builder().patientId(req.getPatientId())
                .detailsJson(req.getDetailsJson()).date(req.getDate()).build();
        return mapRecord(medicalRecordRepository.save(r));
    }

    public List<MedicalRecordResponse> getMedicalHistory(Long pid) {
        return medicalRecordRepository.findByPatientIdOrderByDateDesc(pid).stream().map(this::mapRecord).collect(Collectors.toList());
    }

    private TreatmentResponse mapTreatment(Treatment t) {
        return TreatmentResponse.builder().treatmentId(t.getTreatmentId()).patientId(t.getPatientId())
                .doctorId(t.getDoctorId()).diagnosis(t.getDiagnosis()).prescription(t.getPrescription())
                .date(t.getDate()).status(t.getStatus()).followUpNotes(t.getFollowUpNotes()).createdAt(t.getCreatedAt()).build();
    }

    private MedicalRecordResponse mapRecord(MedicalRecord r) {
        return MedicalRecordResponse.builder().recordId(r.getRecordId()).patientId(r.getPatientId())
                .detailsJson(r.getDetailsJson()).date(r.getDate()).status(r.getStatus()).createdAt(r.getCreatedAt()).build();
    }
}
