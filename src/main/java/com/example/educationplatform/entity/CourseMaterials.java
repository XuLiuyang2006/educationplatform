package com.example.educationplatform.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "课程资料实体类")
@Builder
@Table(name = "course_materials")
public class CourseMaterials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 资料ID

    private Long courseId; // 课程ID
    private String name; // 资料名称
    private String description; // 资料描述
    private String type; // 资料类型（如讲义、参考资料等）
    private String url; // 资料存储路径或URL
    private LocalDateTime uploadedAt; // 上传时间

}
