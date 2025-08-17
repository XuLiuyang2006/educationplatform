package com.example.educationplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDetailDTO {
    private Long id;
    private Long courseId;
    private String title;
    private String description;
    private LocalDateTime dueTime;
    private Double maxScore;

    // 统计信息（教师端）
    private Long totalSubmissions;
    private Long gradedSubmissions;
}
