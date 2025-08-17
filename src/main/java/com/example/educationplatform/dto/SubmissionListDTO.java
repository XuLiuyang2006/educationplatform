package com.example.educationplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionListDTO {
    private Long id;
    private Long assignmentId;
    private Long studentId;
    private LocalDateTime submitTime;
    private Double score;
    private String feedback;
}
