package com.example.educationplatform.controller;


import com.example.educationplatform.annotation.LoginRequired;
import com.example.educationplatform.annotation.RoleRequired;
import com.example.educationplatform.common.Result;
import com.example.educationplatform.dto.StudentCourseCreateDTO;
import com.example.educationplatform.dto.StudentCourseListDTO;
import com.example.educationplatform.enums.RoleEnum;
import com.example.educationplatform.service.StudentCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "学生选课控制器", description = "处理学生选课相关请求的控制器")
@RequestMapping("/api/student/course")
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    //GET接口
    @LoginRequired
    @RoleRequired(RoleEnum.STUDENT)
    @Operation(summary = "获取所有课程", description = "获取所有可选课程列表接口")
    @GetMapping ("/all")
    public Result<?> getAllCourses() {
        return Result.success(studentCourseService.getAllCourses());
    }

    @LoginRequired
    @RoleRequired(RoleEnum.STUDENT)
    @Operation(summary = "获取我的课程", description = "获取学生已选课程列表接口")
    @GetMapping("/my")
    public Result<?> getMyListCourses(HttpSession session) {
        //TODO:studentId的获取方式需要改进，得和userId、身份等关联起来
        Long studentId = (Long) session.getAttribute("userId");
        return Result.success(studentCourseService.getMyCourses(studentId));
    }

    @LoginRequired
    @RoleRequired(RoleEnum.STUDENT)
    @Operation(summary = "获取课程进度", description = "获取学生在某门课程的学习进度接口")
    @GetMapping("/progress")
    public Result<Double> getProgress(@RequestParam Long courseId, HttpSession session) {
        //TODO:studentId的获取方式需要改进，得和userId、身份等关联起来
        Long studentId = (Long) session.getAttribute("userId");
        return Result.success(studentCourseService.getProgress(studentId, courseId));
    }

    @LoginRequired
    @RoleRequired(RoleEnum.STUDENT)
    @Operation(summary = "分页获取我的课程", description = "分页获取学生已选课程列表接口")
    @GetMapping("/page")
    public Result<Page<StudentCourseListDTO>> getMyCoursesPage(
            @RequestParam(defaultValue = "0") int page,
            HttpSession session){
        Long studentId = (Long) session.getAttribute("userId");
        Pageable pageable = PageRequest.of(page, 10);
        Page<StudentCourseListDTO> result = studentCourseService.selectByStudentId(studentId, pageable);
        return Result.success(result);

    }

    //POST接口
    @LoginRequired
    @RoleRequired(RoleEnum.STUDENT)
    @Operation(summary = "选课", description = "学生选课接口")
    @PostMapping("/enroll")
    public Result<Void> enroll(@RequestParam Long courseId, HttpSession session){
        //TODO:我觉得用户登录不能全靠userId来判断，应该把教师、学生、管理员的身份也存进去，与userId一起存储在session中，互相关联起来
        Long studentId = (Long) session.getAttribute("userId");

        studentCourseService.enrollCourse(studentId, courseId);
        return Result.success();
    }

    @LoginRequired
    @RoleRequired(RoleEnum.STUDENT)
    @Operation(summary = "更新课程进度", description = "更新学生在某门课程的学习进度接口")
    @PostMapping("/progress")
    public Result<Void> updateProgress(@RequestBody StudentCourseCreateDTO dto, HttpSession session) {
        //TODO:studentId的获取方式需要改进，得和userId、身份等关联起来
        Long studentId = (Long) session.getAttribute("userId");
        studentCourseService.updateProgress(studentId, dto);
        return Result.success();
    }

    //DELETE接口
    @LoginRequired
    @RoleRequired(RoleEnum.STUDENT)
    @Operation(summary = "退课", description = "学生退课接口")
    @DeleteMapping("/withdraw")
    public Result<Void> withdraw(@RequestParam Long courseId, HttpSession session) {
        //TODO:studentId的获取方式需要改进，得和userId、身份等关联起来
        Long studentId = (Long) session.getAttribute("userId");

        studentCourseService.withdrawCourse(studentId, courseId);
        return Result.success();
    }



}
