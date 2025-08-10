package com.example.educationplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "课程创建数据传输对象")
@Data
public class TeacherCourseCreateDTO {
    private String title;
    private String description;
    private String contentUrl;
}
