package com.codejava.lms.controller;

import com.codejava.lms.entity.Course;
import com.codejava.lms.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public Course create(@Valid @RequestBody Course course) {
        return courseService.create(course);
    }

    @GetMapping("/{id}")
    public Course getById(@PathVariable Long id) {
        return courseService.findById(id);
    }

    @GetMapping
    public List<Course> getAll() {
        return courseService.findAll();
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable Long id,
                         @Valid @RequestBody Course course) {
        return courseService.update(id, course);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        courseService.delete(id);
        return "Course deleted successfully";
    }
}