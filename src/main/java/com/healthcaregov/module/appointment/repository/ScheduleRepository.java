package com.healthcaregov.module.appointment.repository;

import com.healthcaregov.module.appointment.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByDoctorIdAndAvailableDateAndStatus(Long doctorId, LocalDate date, Schedule.SlotStatus status);
}
