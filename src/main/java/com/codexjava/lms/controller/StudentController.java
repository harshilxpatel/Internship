package com.codexjava.lms.controller;

import com.codexjava.lms.entity.Student;
import com.codexjava.lms.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }

    // GET STUDENT BY ID
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.findById(id);
    }

    // CREATE STUDENT
    @PostMapping
    public Student createStudent(@Valid @RequestBody Student student) {
        return studentService.create(student);
    }
}