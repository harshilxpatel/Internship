package com.codexjava.lms.service;

import com.codexjava.lms.entity.Student;

public interface StudentService {
    Student create(Student student);
    Student findById(Long id);
}