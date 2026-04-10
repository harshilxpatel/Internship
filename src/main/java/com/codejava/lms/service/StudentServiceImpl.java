package com.codejava.lms.service;

import com.codejava.lms.entity.Student;
import com.codejava.lms.exception.DuplicateResourceException;
import com.codejava.lms.exception.ResourceNotFoundException;
import com.codejava.lms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student create(Student student) {

        if(studentRepository.findByEmail(student.getEmail()).isPresent()){
            throw new DuplicateResourceException("Email already exists");
        }

        return studentRepository.save(student);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findByDeletedFalse();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .filter(s -> !s.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    @Override
    public Student update(Long id, Student student) {

        Student existing = studentRepository.findById(id)
                .filter(s -> !s.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        // duplicate email check
        studentRepository.findByEmail(student.getEmail())
                .filter(s -> !s.getId().equals(id))
                .ifPresent(s -> {
                    throw new DuplicateResourceException("Email already exists");
                });

        existing.setName(student.getName());
        existing.setEmail(student.getEmail());

        return studentRepository.save(existing);
    }

    @Override
    public void delete(Long id) {

        Student student = studentRepository.findById(id)
                .filter(s -> !s.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        student.setDeleted(true);
        studentRepository.save(student);
    }
}