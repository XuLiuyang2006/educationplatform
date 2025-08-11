package com.example.educationplatform.repository;

import com.example.educationplatform.entity.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {
    long countByVisitTimeBetween(LocalDateTime start, LocalDateTime end);
}
