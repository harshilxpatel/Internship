package com.codejava.lms.controller;

import com.codejava.lms.dto.ApiResponse;
import com.codejava.lms.dto.CourseDTO;
import com.codejava.lms.entity.Student;
import com.codejava.lms.service.CourseService;
import com.codejava.lms.service.EnrollmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Course APIs", description = "Manage courses")
@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    @Operation(summary = "Create new course")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Course created"
    )
    @PostMapping
    public ApiResponse<CourseDTO> create(@RequestBody @Valid CourseDTO dto) {

        return ApiResponse.<CourseDTO>builder()
                .timestamp(LocalDateTime.now())
                .status(201)
                .message("Course created successfully")
                .data(courseService.create(dto))
                .build();
    }

    @Operation(summary = "Get course by ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Course fetched"
    )
    @GetMapping("/{id}")
    public ApiResponse<CourseDTO> getById(@PathVariable Long id) {

        return ApiResponse.<CourseDTO>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Course fetched successfully")
                .data(courseService.findById(id))
                .build();
    }

    @Operation(summary = "Get all courses")
    @GetMapping
    public ApiResponse<List<CourseDTO>> getAll() {

        return ApiResponse.<List<CourseDTO>>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Courses fetched successfully")
                .data(courseService.findAll())
                .build();
    }

    @Operation(summary = "Update course")
    @PutMapping("/{id}")
    public ApiResponse<CourseDTO> update(@PathVariable Long id,
                                         @RequestBody @Valid CourseDTO dto) {

        return ApiResponse.<CourseDTO>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Course updated successfully")
                .data(courseService.update(id, dto))
                .build();
    }

    @Operation(summary = "Delete course (soft delete)")
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {

        courseService.delete(id);

        return ApiResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Course deleted successfully")
                .data("DELETED")
                .build();
    }

    @Operation(summary = "Get students by course")
    @GetMapping("/{id}/students")
    public ApiResponse<List<Student>> getStudentsByCourse(@PathVariable Long id) {

        return ApiResponse.<List<Student>>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Students fetched successfully")
                .data(enrollmentService.getStudentsByCourse(id))
                .build();
    }

    @Operation(summary = "Search courses with pagination")
    @GetMapping("/search")
    public ApiResponse<Page<CourseDTO>> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String code,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "title") String sortBy) {

        return ApiResponse.<Page<CourseDTO>>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Courses fetched successfully")
                .data(courseService.search(title, code, page, size, sortBy))
                .build();
    }
}