package com.example.educationplatform.dto;

import lombok.Data;

@Data
public class SubmissionCreateDTO {
    private Long assignmentId;
    private String content;        // 任选
    private String attachmentUrl;  // 任选（你已经打通上传，这里直接传URL）
}
