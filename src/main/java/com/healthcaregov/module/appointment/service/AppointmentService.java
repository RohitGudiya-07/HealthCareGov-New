package com.healthcaregov.module.appointment.service;

import com.healthcaregov.exception.ResourceNotFoundException;
import com.healthcaregov.exception.SlotUnavailableException;
import com.healthcaregov.module.appointment.dto.*;
import com.healthcaregov.module.appointment.entity.*;
import com.healthcaregov.module.appointment.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest req) {
        return book(req);
    }

    @Transactional
    public AppointmentResponse book(AppointmentRequest req) {
        boolean conflict = appointmentRepository.existsByDoctorIdAndDateAndTimeAndStatusNot(
                req.getDoctorId(), req.getDate(), req.getTime(), Appointment.AppointmentStatus.CANCELLED);
        if (conflict) throw new SlotUnavailableException("Time slot already booked for doctor " + req.getDoctorId());
        Appointment appt = Appointment.builder()
                .patientId(req.getPatientId()).doctorId(req.getDoctorId())
                .date(req.getDate()).time(req.getTime()).notes(req.getNotes()).build();
        appt = appointmentRepository.save(appt);
        log.info("Appointment created: {}", appt.getAppointmentId());
        return mapAppointment(appt);
    }

    public AppointmentResponse getById(Long id) {
        return appointmentRepository.findById(id).map(this::mapAppointment)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found: " + id));
    }

    public List<AppointmentResponse> getAll() {
        return appointmentRepository.findAll().stream().map(this::mapAppointment).collect(Collectors.toList());
    }

    public List<AppointmentResponse> getByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId).stream().map(this::mapAppointment).collect(Collectors.toList());
    }

    public List<AppointmentResponse> getByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId).stream().map(this::mapAppointment).collect(Collectors.toList());
    }

    @Transactional
    public AppointmentResponse updateStatus(Long id, Appointment.AppointmentStatus status) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found: " + id));
        appt.setStatus(status);
        return mapAppointment(appointmentRepository.save(appt));
    }

    @Transactional
    public ScheduleResponse createSchedule(ScheduleRequest req) {
        Schedule schedule = Schedule.builder().doctorId(req.getDoctorId())
                .availableDate(req.getAvailableDate()).timeSlot(req.getTimeSlot()).build();
        schedule = scheduleRepository.save(schedule);
        return mapSchedule(schedule);
    }

    public List<ScheduleResponse> getAvailableSlots(Long doctorId, LocalDate date) {
        return scheduleRepository.findByDoctorIdAndAvailableDateAndStatus(doctorId, date, Schedule.SlotStatus.AVAILABLE)
                .stream().map(this::mapSchedule).collect(Collectors.toList());
    }

    private AppointmentResponse mapAppointment(Appointment a) {
        return AppointmentResponse.builder().appointmentId(a.getAppointmentId())
                .patientId(a.getPatientId()).doctorId(a.getDoctorId())
                .date(a.getDate()).time(a.getTime()).status(a.getStatus())
                .notes(a.getNotes()).createdAt(a.getCreatedAt()).build();
    }

    private ScheduleResponse mapSchedule(Schedule s) {
        return ScheduleResponse.builder().scheduleId(s.getScheduleId()).doctorId(s.getDoctorId())
                .availableDate(s.getAvailableDate()).timeSlot(s.getTimeSlot()).status(s.getStatus()).build();
    }
}
