package com.healthcaregov.module.reporting.repository;

import com.healthcaregov.module.reporting.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByScope(Report.ReportScope scope);
    long countByScope(Report.ReportScope scope);
}
