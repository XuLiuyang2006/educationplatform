package com.example.educationplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "student_profile")
@Data
public class StudentProfile {
    @Id
    @Column(name = "user_id")
    private Long userId; // 直接对应 User.id（这里就不要再自增了）

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user; // 映射到 User 表

    private String major;   // 专业
    private String grade;   // 年级
    private String interests; // 兴趣标签（JSON 或逗号分隔）
    private String goals;     // 学习目标（升学/就业/兴趣）

    private String historyCourses; // 历史选课记录（可以后续优化为单独表）

    private Double avgProgress; // 平均完成率
    private Integer totalStudyHours; // 总学习时长
}
