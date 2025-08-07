package com.example.educationplatform.dto;

import com.example.educationplatform.enums.CourseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "课程数据传输对象")
@Data
public class CourseDTO {
    private Long id;

    private String title;
    private String description;
    private String contentUrl;

    private Long teacherId;

    private CourseStatus status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
