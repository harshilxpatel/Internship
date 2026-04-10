package com.codejava.lms.controller;

import com.codejava.lms.dto.ApiResponse;
import com.codejava.lms.dto.EnrollmentRequestDTO;
import com.codejava.lms.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Enrollment APIs", description = "Manage enrollments")
@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

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
}