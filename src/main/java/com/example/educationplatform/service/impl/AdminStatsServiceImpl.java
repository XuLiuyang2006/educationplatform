package com.example.educationplatform.service.impl;

import com.example.educationplatform.entity.User;
import com.example.educationplatform.enums.RoleEnum;
import com.example.educationplatform.repository.CourseRepository;
import com.example.educationplatform.repository.StudentCourseRepository;
import com.example.educationplatform.repository.UserRepository;
import com.example.educationplatform.repository.VisitLogRepository;
import com.example.educationplatform.service.AdminStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AdminStatsServiceImpl implements AdminStatsService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final VisitLogRepository visitLogRepository;

    @Override
    public long getCourseCount() {
        return courseRepository.count();
    }

    @Override
    public long getStudentCount() {
        return userRepository.countByRole(RoleEnum.STUDENT);
    }

    @Override
    public long getTeacherCount() {
        return userRepository.countByRole(RoleEnum.TEACHER);
    }

    @Override
    public long getNewUserCount(LocalDate start, LocalDate end) {
        return userRepository.countByCreateTimeBetween(start.atStartOfDay(), end.plusDays(1).atStartOfDay());
    }

    @Override
    public long getNewCourseCount(LocalDate start, LocalDate end) {
        return courseRepository.countByCreateTimeBetween(start.atStartOfDay(), end.plusDays(1).atStartOfDay());
    }

    @Override
    public long getCourseSelectionCount(LocalDate start, LocalDate end) {
        return studentCourseRepository.countBySelectedAtBetween(
                start.atStartOfDay(),
                end.plusDays(1).atStartOfDay()
        );
    }

    @Override
    public long getVisitCount(LocalDate start, LocalDate end) {
        return visitLogRepository.countByVisitTimeBetween(
                start.atStartOfDay(),
                end.plusDays(1).atStartOfDay()
        );
    }
}
