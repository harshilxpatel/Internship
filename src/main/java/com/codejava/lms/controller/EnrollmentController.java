package com.codejava.lms.controller;

import com.codejava.lms.dto.ApiResponse;
import com.codejava.lms.dto.EnrollmentRequestDTO;
import com.codejava.lms.service.EnrollmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Enrollment APIs", description = "Manage enrollments")
@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    // =========================
    // ENROLL STUDENT
    // =========================
    @Operation(summary = "Enroll student to course")
    @PostMapping
    public ApiResponse<String> enroll(@RequestBody @Valid EnrollmentRequestDTO dto) {

        String result = enrollmentService.enroll(dto.getStudentId(), dto.getCourseId());

        return ApiResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message(result)
                .data("SUCCESS")
                .build();
    }

    // =========================
    // UNENROLL STUDENT
    // =========================
    @Operation(summary = "Unenroll student")
    @PutMapping("/unenroll")
    public ApiResponse<String> unenroll(@RequestBody @Valid EnrollmentRequestDTO dto) {

        String result = enrollmentService.unenroll(dto.getStudentId(), dto.getCourseId());

        return ApiResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message(result)
                .data("SUCCESS")
                .build();
    }

    // =========================
    // GET COURSES BY STUDENT
    // =========================
    @Operation(summary = "Get all courses by student")
    @GetMapping("/courses/{studentId}")
    public ApiResponse<?> getCoursesByStudent(@PathVariable Long studentId) {

        return ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Courses fetched successfully")
                .data(enrollmentService.getCoursesByStudent(studentId))
                .build();
    }

    // =========================
    // GET STUDENTS BY COURSE
    // =========================
    @Operation(summary = "Get all students by course")
    @GetMapping("/students/{courseId}")
    public ApiResponse<?> getStudentsByCourse(@PathVariable Long courseId) {

        return ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Students fetched successfully")
                .data(enrollmentService.getStudentsByCourse(courseId))
                .build();
    }
}