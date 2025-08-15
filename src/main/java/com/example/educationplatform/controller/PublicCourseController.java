package com.example.educationplatform.controller;

import com.example.educationplatform.annotation.LoginRequired;
import com.example.educationplatform.common.Result;
import com.example.educationplatform.dto.PublicCourseDetailDTO;
import com.example.educationplatform.dto.PublicCourseListDTO;
import com.example.educationplatform.service.PublicCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@Tag(name = "公开课程控制器", description = "处理公开课程相关请求的控制器")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class PublicCourseController {

    private final PublicCourseService publicCourseService;

    @Operation(summary = "获取所有公开课程", description = "分页获取所有公开课程列表接口")
    @GetMapping("/courses")
    public Page<PublicCourseListDTO> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page,size);
        return publicCourseService.getAllCoursesList(pageable);
    }

    @Operation(summary = "获取课程详情", description = "获取指定课程的详细信息接口")
    @GetMapping("/course/detail/{id}")
    @LoginRequired
    public Result<PublicCourseDetailDTO> getCourseDetail(@PathVariable Long id) {
        PublicCourseDetailDTO courseDetailDTO = publicCourseService.getCourseDetail(id);
        return Result.success(courseDetailDTO);
    }
}
