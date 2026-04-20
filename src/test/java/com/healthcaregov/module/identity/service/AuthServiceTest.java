package com.healthcaregov.module.identity.service;

import com.healthcaregov.exception.DuplicateResourceException;
import com.healthcaregov.module.identity.dto.*;
import com.healthcaregov.module.identity.entity.*;
import com.healthcaregov.module.identity.repository.*;
import com.healthcaregov.security.JwtUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Tests")
class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private AuditLogRepository auditLogRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtUtil jwtUtil;
    @InjectMocks private AuthService authService;

    private RegisterRequest registerRequest;
    private User mockUser;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setName("Test Doctor");
        registerRequest.setEmail("doctor@hospital.com");
        registerRequest.setPassword("Test@1234");
        registerRequest.setRole(User.Role.DOCTOR);
        registerRequest.setPhone("9876543210");

        mockUser = User.builder().userId(1L).name("Test Doctor")
                .email("doctor@hospital.com").role(User.Role.DOCTOR)
                .status(User.UserStatus.ACTIVE).build();
    }

    @Test @DisplayName("Register user successfully")
    void shouldRegisterUser() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any())).thenReturn(mockUser);
        when(auditLogRepository.save(any())).thenReturn(new AuditLog());

        UserResponse result = authService.register(registerRequest);

        assertNotNull(result);
        assertEquals("Test Doctor", result.getName());
        verify(userRepository).save(any());
    }

    @Test @DisplayName("Throw error for duplicate email")
    void shouldThrowForDuplicateEmail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        assertThrows(DuplicateResourceException.class, () -> authService.register(registerRequest));
        verify(userRepository, never()).save(any());
    }

    @Test @DisplayName("Login successfully and return token")
    void shouldLoginSuccessfully() {
        LoginRequest loginReq = new LoginRequest();
        loginReq.setEmail("doctor@hospital.com");
        loginReq.setPassword("Test@1234");

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(jwtUtil.generateToken(anyString(), anyString(), anyLong())).thenReturn("mock-jwt");
        when(jwtUtil.getExpirationMs()).thenReturn(86400000L);

        AuthResponse result = authService.login(loginReq);

        assertNotNull(result);
        assertEquals("mock-jwt", result.getToken());
        assertEquals("Bearer", result.getTokenType());
    }

    @Test @DisplayName("Throw error for wrong password")
    void shouldThrowForWrongPassword() {
        LoginRequest loginReq = new LoginRequest();
        loginReq.setEmail("doctor@hospital.com");
        loginReq.setPassword("wrong");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () -> authService.login(loginReq));
    }
}
