package com.example.educationplatform.repository;

import com.example.educationplatform.entity.AiChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiChatRepository extends JpaRepository<AiChat, Long> {
    List<AiChat> findByUserId(Long userId);
}
