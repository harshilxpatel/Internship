package com.codejava.lms.controller;

import com.codejava.lms.entity.Student;
import com.codejava.lms.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // GET ALL
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.findById(id);
    }

    // CREATE
    @PostMapping
    public Student createStudent(@Valid @RequestBody Student student) {
        return studentService.create(student);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id,
                                 @Valid @RequestBody Student student) {
        return studentService.update(id, student);
    }

    // ✅ DELETE (SOFT)
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return "Student deleted successfully";
    }
}