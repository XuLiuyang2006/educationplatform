package com.example.educationplatform.service.impl;

import com.example.educationplatform.dto.AiChatDTO;
import com.example.educationplatform.entity.AiChat;
import com.example.educationplatform.repository.AiChatRepository;
import com.example.educationplatform.service.AiChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiChatServiceImpl implements AiChatService {

    private final AiChatRepository aiChatRepository;

    @Override
    public AiChatDTO askQuestion(Long userId, String question) {
        // TODO: 调用讯飞大模型 API，这里先写假数据
        String answer = "这是AI的回答（测试版）";

        AiChat aiChat = new AiChat();
        aiChat.setUserId(userId);
        aiChat.setQuestion(question);
        aiChat.setAnswer(answer);
        aiChatRepository.save(aiChat);

        return new AiChatDTO(question, answer);
    }

    @Override
    public List<AiChatDTO> getHistory(Long userId) {
        return aiChatRepository.findByUserId(userId)
                .stream()
                .map(chat -> new AiChatDTO(chat.getQuestion(), chat.getAnswer()))
                .toList();
    }
}
