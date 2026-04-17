package com.codejava.lms.service;

import com.codejava.lms.entity.Course;
import com.codejava.lms.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public Course create(Course course) {

        courseRepository.findByCode(course.getCode())
                .ifPresent(c -> {
                    throw new RuntimeException("Course code already exists");
                });

        return courseRepository.save(course);
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findByDeletedFalse();
    }

    @Override
    public Course update(Long id, Course course) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        existing.setTitle(course.getTitle());
        existing.setCode(course.getCode());
        existing.setCredits(course.getCredits());

        return courseRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setDeleted(true);
        courseRepository.save(course);
    }
}