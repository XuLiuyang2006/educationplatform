package com.example.educationplatform.controller;

import com.example.educationplatform.common.Result;
import com.example.educationplatform.dto.AiChatDTO;
import com.example.educationplatform.service.AiChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
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
    @Operation(summary = "向AI提问", description = "用户可以向AI提问，AI会返回回答")
    @PostMapping("/ask")
    public Result<AiChatDTO> askQuestion(HttpSession session,
                                         @RequestParam String question) {
        Long userId = (Long) session.getAttribute("userId");
        return Result.success(aiChatService.askQuestion(userId, question));
    }

    // 获取历史
    @Operation(summary = "获取用户提问历史", description = "用户可以查看自己的提问历史记录")
    @GetMapping("/history/me")
    public Result<List<AiChatDTO>> getHistory(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return Result.success(aiChatService.getHistory(userId));
    }

    //获取所有历史（管理员权限）
    @Operation(summary = "获取所有用户提问历史", description = "管理员可以查看所有用户的提问历史记录")
    @GetMapping("/history/all")
    public Result<List<AiChatDTO>> getAllHistory() {
        return Result.success(aiChatService.getAllHistory());
    }

    //获取某个用户的历史（管理员权限）
    @Operation(summary = "获取指定用户提问历史", description = "管理员可以查看指定用户的提问历史记录")
    @GetMapping("/history/user/{userId}")
    public Result<List<AiChatDTO>> getUserHistory(@PathVariable Long userId) {
        return Result.success(aiChatService.getHistory(userId));
    }
}
