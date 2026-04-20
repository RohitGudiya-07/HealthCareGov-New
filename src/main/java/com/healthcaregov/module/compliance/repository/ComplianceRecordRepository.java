package com.healthcaregov.module.compliance.repository;

import com.healthcaregov.module.compliance.entity.ComplianceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComplianceRecordRepository extends JpaRepository<ComplianceRecord, Long> {
    List<ComplianceRecord> findByEntityId(Long entityId);
    List<ComplianceRecord> findByResult(ComplianceRecord.ComplianceResult result);
}
