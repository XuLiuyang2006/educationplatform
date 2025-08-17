package com.example.educationplatform.dto;

import lombok.Data;

@Data
public class SubmissionGradeDTO {
    private Long submissionId;
    private Double score;
    private String feedback;
}
