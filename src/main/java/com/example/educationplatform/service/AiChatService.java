package com.example.educationplatform.service;

import com.example.educationplatform.dto.AiChatDTO;

import java.util.List;

public interface AiChatService {
    AiChatDTO askQuestion(Long userId, String question);
    List<AiChatDTO> getHistory(Long userId);
    List<AiChatDTO> getAllHistory();
}
