package com.codexjava.lms.service;

import com.codexjava.lms.entity.Student;
import com.codexjava.lms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student create(Student student) {
        studentRepository.findByEmail(student.getEmail())
                .ifPresent(s -> {
                    throw new RuntimeException("Email already exists");
                });

        return studentRepository.save(student);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }
}