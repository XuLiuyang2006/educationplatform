package com.example.educationplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicCourseListDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createTime;
}
