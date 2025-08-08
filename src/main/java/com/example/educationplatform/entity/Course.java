package com.example.educationplatform.entity;

import com.example.educationplatform.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String contentUrl; // 视频或文档路径

    private Long teacherId; // 发布人

    private String teacherName; // 发布人姓名

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.status = CourseStatus.PENDING; // 默认待审核
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateTime = LocalDateTime.now();
    }
}
