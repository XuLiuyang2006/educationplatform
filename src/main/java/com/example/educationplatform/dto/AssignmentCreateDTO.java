package com.example.educationplatform.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AssignmentCreateDTO {
    private Long courseId;
    private String title;
    private String description;
    private LocalDateTime dueTime;
    private Double maxScore;
}
