package com.healthcaregov.module.identity.service;

import com.healthcaregov.exception.*;
import com.healthcaregov.module.identity.dto.*;
import com.healthcaregov.module.identity.entity.*;
import com.healthcaregov.module.identity.repository.*;
import com.healthcaregov.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new DuplicateResourceException("Email already registered: " + request.getEmail());

        User user = User.builder()
                .name(request.getName()).role(request.getRole())
                .email(request.getEmail()).phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(User.UserStatus.ACTIVE).build();
        user = userRepository.save(user);

        auditLogRepository.save(AuditLog.builder()
                .userId(user.getUserId()).action("REGISTER")
                .resource("User").outcome("SUCCESS").build());
        log.info("User registered: {}", user.getEmail());
        return mapUser(user);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getUserId());

        auditLogRepository.save(AuditLog.builder()
                .userId(user.getUserId()).action("LOGIN")
                .resource("Auth").outcome("SUCCESS").build());
        return AuthResponse.builder().token(token).tokenType("Bearer")
                .userId(user.getUserId()).name(user.getName())
                .email(user.getEmail()).role(user.getRole())
                .expiresIn(jwtUtil.getExpirationMs()).build();
    }

    private UserResponse mapUser(User u) {
        return UserResponse.builder().userId(u.getUserId()).name(u.getName())
                .role(u.getRole()).email(u.getEmail()).phone(u.getPhone())
                .status(u.getStatus()).createdAt(u.getCreatedAt()).build();
    }
}
