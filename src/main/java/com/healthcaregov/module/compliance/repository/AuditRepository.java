package com.healthcaregov.module.compliance.repository;

import com.healthcaregov.module.compliance.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    List<Audit> findByOfficerId(Long officerId);
}
