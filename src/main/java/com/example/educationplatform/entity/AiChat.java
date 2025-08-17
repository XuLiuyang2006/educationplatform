package com.example.educationplatform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AiChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 主键ID,用于标识每条聊天记录

    private Long userId;       // 谁提问的
    private String question;   // 用户的问题
    private String answer;     // AI 的回答

    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime = LocalDateTime.now();
}
