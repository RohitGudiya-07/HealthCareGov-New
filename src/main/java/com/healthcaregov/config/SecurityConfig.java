package com.healthcaregov.config;

import com.healthcaregov.security.JwtAuthenticationFilter;
import com.healthcaregov.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a
                        // Public
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()

                        // Identity — User Management
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/users", "/api/v1/users/**")
                        .hasAnyRole("HOSPITAL_ADMIN", "GOVERNMENT_AUDITOR")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/v1/users/**")
                        .hasRole("HOSPITAL_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/v1/users/**")
                        .hasRole("HOSPITAL_ADMIN")

                        // Patient Management
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/patients", "/api/v1/patients/documents")
                        .hasAnyRole("HOSPITAL_ADMIN", "DOCTOR")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/v1/patients/**")
                        .hasAnyRole("HOSPITAL_ADMIN", "DOCTOR")
                        .requestMatchers(org.springframework.http.HttpMethod.PATCH, "/api/v1/patients/**")
                        .hasAnyRole("HOSPITAL_ADMIN", "DOCTOR")
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/patients/**")
                        .hasAnyRole("DOCTOR", "HOSPITAL_ADMIN", "COMPLIANCE_OFFICER", "GOVERNMENT_AUDITOR")

                        // Appointments
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/appointments")
                        .hasAnyRole("PATIENT", "DOCTOR", "HOSPITAL_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/appointments/schedules")
                        .hasAnyRole("DOCTOR", "HOSPITAL_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/appointments/schedules/**")
                        .hasAnyRole("PATIENT", "DOCTOR", "HOSPITAL_ADMIN", "PROGRAM_MANAGER")
                        .requestMatchers(org.springframework.http.HttpMethod.PATCH, "/api/v1/appointments/**")
                        .hasAnyRole("DOCTOR", "HOSPITAL_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/appointments/**")
                        .hasAnyRole("DOCTOR", "HOSPITAL_ADMIN", "PROGRAM_MANAGER", "GOVERNMENT_AUDITOR")

                        // Treatments & Medical Records
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/treatments", "/api/v1/treatments/medical-records")
                        .hasRole("DOCTOR")
                        .requestMatchers(org.springframework.http.HttpMethod.PATCH, "/api/v1/treatments/**")
                        .hasRole("DOCTOR")
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/treatments/**")
                        .hasAnyRole("DOCTOR", "PATIENT", "COMPLIANCE_OFFICER", "GOVERNMENT_AUDITOR")

                        // Hospital & Resource Management
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/hospitals", "/api/v1/hospitals/resources")
                        .hasRole("HOSPITAL_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/v1/hospitals/**")
                        .hasRole("HOSPITAL_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PATCH, "/api/v1/hospitals/**")
                        .hasRole("HOSPITAL_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/hospitals/**")
                        .authenticated()

                        // Compliance & Audits
                        .requestMatchers("/api/v1/compliance/**")
                        .hasAnyRole("COMPLIANCE_OFFICER", "GOVERNMENT_AUDITOR")

                        // Reporting
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/reports/**")
                        .hasAnyRole("PROGRAM_MANAGER", "HOSPITAL_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/reports/**")
                        .hasAnyRole("PROGRAM_MANAGER", "HOSPITAL_ADMIN", "GOVERNMENT_AUDITOR", "COMPLIANCE_OFFICER")

                        // Notifications — any authenticated user manages their own
                        .requestMatchers("/api/v1/notifications/**").authenticated()

                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
