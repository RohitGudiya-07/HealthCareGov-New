package com.healthcaregov.module.identity.service;

import com.healthcaregov.exception.ResourceNotFoundException;
import com.healthcaregov.module.identity.dto.*;
import com.healthcaregov.module.identity.entity.*;
import com.healthcaregov.module.identity.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapUser).collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id).map(this::mapUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest req) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        if (req.getName() != null) u.setName(req.getName());
        if (req.getPhone() != null) u.setPhone(req.getPhone());
        if (req.getStatus() != null) u.setStatus(req.getStatus());
        return mapUser(userRepository.save(u));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new ResourceNotFoundException("User not found: " + id);
        userRepository.deleteById(id);
    }

    public List<AuditLog> getAuditLogs(Long userId) {
        return auditLogRepository.findByUserId(userId);
    }

    private UserResponse mapUser(User u) {
        return UserResponse.builder().userId(u.getUserId()).name(u.getName())
                .role(u.getRole()).email(u.getEmail()).phone(u.getPhone())
                .status(u.getStatus()).createdAt(u.getCreatedAt()).build();
    }
}
