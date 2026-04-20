package com.healthcaregov.module.appointment.repository;

import com.healthcaregov.module.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
    boolean existsByDoctorIdAndDateAndTimeAndStatusNot(Long doctorId, LocalDate date, LocalTime time, Appointment.AppointmentStatus status);
}
