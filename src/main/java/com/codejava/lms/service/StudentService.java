package com.codejava.lms.service;

import com.codejava.lms.entity.Student;

import java.util.List;

public interface StudentService {

    Student create(Student student);

    Student findById(Long id);

    List<Student> findAll();

    Student update(Long id, Student student);

    void delete(Long id);
}