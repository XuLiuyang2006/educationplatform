package com.example.educationplatform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_course")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 选课记录ID，不是课程ID，用于标识选课记录

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private Double progress; // 0.0 - 1.0 表示进度百分比

    private LocalDateTime selectedAt; // 选课时间

    private String title; // 课程标题，方便查询

    // 关键：增加到 Course 的关联
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id",  updatable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private StudentProfile studentProfile;


    @PrePersist
    protected void onCreate() {
        if (selectedAt == null) selectedAt = LocalDateTime.now();
        if (progress == null) progress = 0.0;
    }
}