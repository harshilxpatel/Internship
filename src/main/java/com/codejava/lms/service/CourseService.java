package com.codejava.lms.service;

import com.codejava.lms.entity.Course;

import java.util.List;

public interface CourseService {

    Course create(Course course);

    Course findById(Long id);

    List<Course> findAll();

    Course update(Long id, Course course);

    void delete(Long id);
}