package com.example.educationplatform.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignment_submissions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"assignment_id","student_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assignment_id", nullable = false)
    private Long assignmentId;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(columnDefinition = "TEXT")
    private String content;          // 在线作答内容（可选）

    private String attachmentUrl;    // 附件（可选）

    private LocalDateTime submitTime;

    private Double score;            // 评分（教师批改后）

    private String feedback;         // 评语

    private LocalDateTime gradeTime; // 批改时间

    @PrePersist
    protected void onCreate() {
        this.submitTime = LocalDateTime.now();
    }
}
