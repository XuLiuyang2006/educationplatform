package com.example.educationplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "teacher_profile")
@Data
public class TeacherProfile {
    @Id
    private Long userId; // 直接对应 User.id

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user; // 映射到 User 表

    private String expertise;  // 教授方向 / 专业领域
    private String tags;       // 课程标签（AI、大数据、文学等）
    private String teachingStyle; // 教学风格
    private Double avgRating;     // 学生反馈评分
}
