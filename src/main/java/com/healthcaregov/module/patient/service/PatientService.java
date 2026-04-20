package com.healthcaregov.module.patient.service;

import com.healthcaregov.exception.DuplicateResourceException;
import com.healthcaregov.exception.ResourceNotFoundException;
import com.healthcaregov.module.patient.dto.*;
import com.healthcaregov.module.patient.entity.*;
import com.healthcaregov.module.patient.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientDocumentRepository documentRepository;

    @Transactional
    public PatientResponse createPatient(PatientRequest req) {
        if (req.getEmail() != null && patientRepository.existsByEmail(req.getEmail())) {
            throw new DuplicateResourceException("Patient with email already exists: " + req.getEmail());
        }
        Patient patient = Patient.builder()
                .name(req.getName()).dob(req.getDob()).gender(req.getGender())
                .address(req.getAddress()).contactInfo(req.getContactInfo())
                .email(req.getEmail()).phone(req.getPhone()).build();
        patient = patientRepository.save(patient);
        log.info("Patient created: {}", patient.getPatientId());
        return mapPatient(patient);
    }

    // alias used by tests
    @Transactional
    public PatientResponse registerPatient(PatientRequest req) { return createPatient(req); }

    public PatientResponse getById(Long id) {
        return patientRepository.findById(id).map(this::mapPatient)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + id));
    }

    public List<PatientResponse> getAll() {
        return patientRepository.findAll().stream().map(this::mapPatient).collect(Collectors.toList());
    }

    @Transactional
    public PatientResponse update(Long id, PatientRequest req) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + id));
        patient.setName(req.getName());
        patient.setDob(req.getDob());
        if (req.getGender() != null) patient.setGender(req.getGender());
        if (req.getAddress() != null) patient.setAddress(req.getAddress());
        if (req.getContactInfo() != null) patient.setContactInfo(req.getContactInfo());
        if (req.getEmail() != null) patient.setEmail(req.getEmail());
        if (req.getPhone() != null) patient.setPhone(req.getPhone());
        return mapPatient(patientRepository.save(patient));
    }

    @Transactional
    public PatientResponse updateStatus(Long id, Patient.PatientStatus status) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + id));
        patient.setStatus(status);
        return mapPatient(patientRepository.save(patient));
    }

    @Transactional
    public DocumentResponse uploadDocument(DocumentRequest req) {
        patientRepository.findById(req.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + req.getPatientId()));
        PatientDocument doc = PatientDocument.builder()
                .patientId(req.getPatientId()).docType(req.getDocType()).fileUri(req.getFileUri()).build();
        doc = documentRepository.save(doc);
        log.info("Document uploaded for patient: {}", req.getPatientId());
        return mapDocument(doc);
    }

    public List<DocumentResponse> getDocuments(Long patientId) {
        return documentRepository.findByPatientId(patientId).stream().map(this::mapDocument).collect(Collectors.toList());
    }

    private PatientResponse mapPatient(Patient p) {
        return PatientResponse.builder().patientId(p.getPatientId()).name(p.getName())
                .dob(p.getDob()).gender(p.getGender()).address(p.getAddress())
                .contactInfo(p.getContactInfo()).email(p.getEmail()).phone(p.getPhone())
                .status(p.getStatus()).createdAt(p.getCreatedAt()).build();
    }

    private DocumentResponse mapDocument(PatientDocument d) {
        return DocumentResponse.builder().documentId(d.getDocumentId()).patientId(d.getPatientId())
                .docType(d.getDocType()).fileUri(d.getFileUri())
                .uploadedDate(d.getUploadedDate()).verificationStatus(d.getVerificationStatus()).build();
    }
}
