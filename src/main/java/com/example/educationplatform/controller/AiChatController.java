package com.example.educationplatform.controller;

import com.example.educationplatform.common.Result;
import com.example.educationplatform.dto.AiChatDTO;
import com.example.educationplatform.service.AiChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "AI聊天接口")
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiChatController {

    private final AiChatService aiChatService;

    // 提问
    @PostMapping("/ask")
    public Result<AiChatDTO> askQuestion(@RequestParam Long userId,
                                         @RequestParam String question) {
        return Result.success(aiChatService.askQuestion(userId, question));
    }

    // 获取历史
    @GetMapping("/history/{userId}")
    public Result<List<AiChatDTO>> getHistory(@PathVariable Long userId) {
        return Result.success(aiChatService.getHistory(userId));
    }
}
