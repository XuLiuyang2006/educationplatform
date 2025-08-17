package com.example.educationplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentListDTO {
    private Long id;
    private Long courseId;
    private String title;
    private LocalDateTime dueTime;
    private Double maxScore;
    // 学生端可选：是否已提交
    private Boolean submitted;
}
