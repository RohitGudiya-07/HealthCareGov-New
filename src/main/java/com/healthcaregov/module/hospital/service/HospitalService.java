package com.healthcaregov.module.hospital.service;

import com.healthcaregov.exception.ResourceNotFoundException;
import com.healthcaregov.module.hospital.dto.*;
import com.healthcaregov.module.hospital.entity.*;
import com.healthcaregov.module.hospital.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final ResourceRepository resourceRepository;

    @Transactional
    public HospitalResponse createHospital(HospitalRequest req) {
        Hospital h = Hospital.builder().name(req.getName()).location(req.getLocation())
                .capacity(req.getCapacity()).contactNumber(req.getContactNumber()).email(req.getEmail()).build();
        h = hospitalRepository.save(h);
        log.info("Hospital created: {}", h.getHospitalId());
        return mapHospital(h);
    }

    public HospitalResponse getById(Long id) {
        return hospitalRepository.findById(id).map(this::mapHospital)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital not found: " + id));
    }

    public List<HospitalResponse> getAll() {
        return hospitalRepository.findAll().stream().map(this::mapHospital).collect(Collectors.toList());
    }

    @Transactional
    public HospitalResponse update(Long id, HospitalRequest req) {
        Hospital h = hospitalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital not found: " + id));
        h.setName(req.getName()); h.setLocation(req.getLocation()); h.setCapacity(req.getCapacity());
        if (req.getContactNumber() != null) h.setContactNumber(req.getContactNumber());
        if (req.getEmail() != null) h.setEmail(req.getEmail());
        return mapHospital(hospitalRepository.save(h));
    }

    @Transactional
    public ResourceResponse addResource(ResourceRequest req) {
        hospitalRepository.findById(req.getHospitalId())
                .orElseThrow(() -> new ResourceNotFoundException("Hospital not found: " + req.getHospitalId()));
        Resource r = Resource.builder().hospitalId(req.getHospitalId()).type(req.getType())
                .quantity(req.getQuantity()).availableQuantity(req.getAvailableQuantity())
                .description(req.getDescription()).build();
        return mapResource(resourceRepository.save(r));
    }

    public List<ResourceResponse> getResources(Long hospitalId) {
        return resourceRepository.findByHospitalId(hospitalId).stream().map(this::mapResource).collect(Collectors.toList());
    }

    @Transactional
    public ResourceResponse updateResourceStatus(Long resourceId, Resource.ResourceStatus status) {
        Resource r = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found: " + resourceId));
        r.setStatus(status);
        return mapResource(resourceRepository.save(r));
    }

    private HospitalResponse mapHospital(Hospital h) {
        return HospitalResponse.builder().hospitalId(h.getHospitalId()).name(h.getName())
                .location(h.getLocation()).capacity(h.getCapacity()).status(h.getStatus())
                .contactNumber(h.getContactNumber()).email(h.getEmail()).createdAt(h.getCreatedAt()).build();
    }

    private ResourceResponse mapResource(Resource r) {
        return ResourceResponse.builder().resourceId(r.getResourceId()).hospitalId(r.getHospitalId())
                .type(r.getType()).quantity(r.getQuantity()).availableQuantity(r.getAvailableQuantity())
                .status(r.getStatus()).description(r.getDescription()).createdAt(r.getCreatedAt()).build();
    }
}
