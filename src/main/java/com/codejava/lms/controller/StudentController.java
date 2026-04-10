package com.codejava.lms.controller;


import com.codejava.lms.dto.ApiResponse;
import com.codejava.lms.dto.CourseDTO;
import com.codejava.lms.entity.Student;
import com.codejava.lms.service.EnrollmentService;
import com.codejava.lms.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Student APIs", description = "Manage students")
@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final EnrollmentService enrollmentService;

    @Operation(summary = "Get all students")
    @GetMapping
    public ApiResponse<List<Student>> getAllStudents() {
        return ApiResponse.<List<Student>>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Students fetched successfully")
                .data(studentService.findAll())
                .build();
    }

    @Operation(summary = "Get student by ID")
    @GetMapping("/{id}")
    public ApiResponse<Student> getStudent(@PathVariable Long id) {
        return ApiResponse.<Student>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Student fetched successfully")
                .data(studentService.findById(id))
                .build();
    }

    @Operation(summary = "Create student")
    @PostMapping
    public ApiResponse<Student> createStudent(@Valid @RequestBody Student student) {
        Student saved = studentService.create(student);

        return ApiResponse.<Student>builder()
                .timestamp(LocalDateTime.now())
                .status(201)
                .message("Student created successfully")
                .data(saved)
                .build();
    }

    @Operation(summary = "Update student")
    @PutMapping("/{id}")
    public ApiResponse<Student> updateStudent(@PathVariable Long id,
                                              @RequestBody @Valid Student student) {
        return ApiResponse.<Student>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Student updated successfully")
                .data(studentService.update(id, student))
                .build();
    }

    @Operation(summary = "Delete student")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteStudent(@PathVariable Long id) {
        studentService.delete(id);

        return ApiResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Student deleted successfully")
                .data("DELETED")
                .build();
    }

    @Operation(summary = "Get courses by student")
    @GetMapping("/{id}/courses")
    public ApiResponse<List<CourseDTO>> getStudentCourses(@PathVariable Long id) {
        return ApiResponse.<List<CourseDTO>>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Courses fetched successfully")
                .data(enrollmentService.getCoursesByStudent(id))
                .build();
    }
}